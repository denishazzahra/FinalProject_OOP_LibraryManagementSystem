/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.*;
/**
 *
 * @author LENOVO
 */
public class LibraryModel {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/librarymanagementsystem";
    static final String USER = "root";
    static final String PASS = ""; 
    public Connection connection;
    public Statement statement;
    public LibraryModel() {
        try{
            Class.forName(JDBC_DRIVER);
            connection = (java.sql.Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected successfully!");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Failed to connect!");
        }
    }
    public boolean loginAdmin(String username, String password){
        try{
            String query = "Select COUNT(*) as total from admin WHERE username='"+username+"' and password=BINARY '"+password+"'";
            statement = connection.createStatement();
            ResultSet result=statement.executeQuery(query);
            while(result.next())
            {
                if(result.getString("total").equals("1"))
                {
                    return true;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
    public boolean loginUser(String username, String password){
        try{
            String query = "Select COUNT(*) as total from user WHERE username='"+username+"' and password=BINARY '"+password+"'";
            statement = connection.createStatement();
            ResultSet result=statement.executeQuery(query);
            while(result.next())
            {
                if(result.getString("total").equals("1"))
                {
                    return true;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
    public boolean usernameCheck(String username){
        try{
            String query = "Select COUNT(*) as total from user WHERE username='"+username+"'";
            statement = connection.createStatement();
            ResultSet result=statement.executeQuery(query);
            while(result.next())
            {
                if(result.getString("total").equals("1"))
                {
                    return false;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return true;
    }
    public void registerUser(String username,String password){
        try{
            String query = "INSERT INTO user VALUES ('"+username+"','"+password+"')";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Sign up success!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String[][] readGenre(){
        String[][] genre=new String[numOfGenre()][2];
        try{
            int totaldata = 0;
            String query = "Select * from genre";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                genre[totaldata][0] = resultSet.getString("genre_id"); 
                genre[totaldata][1] = resultSet.getString("genre"); 
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return genre;
    }
    public int numOfGenre(){
        int totaldata=0;
        try{
            String query = "Select * from genre";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public void addBook(String title, String author, int year, int genre_id){
        try{
            String query = "INSERT INTO `book` (`book_id`, `title`, `author`, `year`, `genre_id`, `available`) VALUES (NULL,'"+title+"','"+author+"','"+year+"','"+genre_id+"',1)";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Add book success!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String getGenre(String id){
        String genre="";
        int gid=Integer.parseInt(id);
        try{
            String query = "Select genre from genre WHERE genre_id='"+gid+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                genre=resultSet.getString("genre"); 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return genre;
    }
    public String[][] readBook(){
        String[][] data=new String[numOfBook()][6];
        try{
            int totaldata = 0;
            String query = "Select * from book";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("book_id"); 
                data[totaldata][1] = resultSet.getString("title"); 
                data[totaldata][2] = resultSet.getString("author"); 
                data[totaldata][3] = resultSet.getString("year"); 
                data[totaldata][4] = getGenre(resultSet.getString("genre_id")); 
                if(resultSet.getString("available").equals("1")){
                    data[totaldata][5] = "Yes";
                }
                else{
                    data[totaldata][5] = "No";
                }
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfBook(){
        int totaldata=0;
        try{
            String query = "Select * from book";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public String[][] readSearch(String search){
        String[][] data=new String[numOfSearch(search)][6];
        try{
            int totaldata = 0;
            String query = "Select book_id,title,author,year,genre,available from book INNER JOIN genre on genre.genre_id=book.genre_id WHERE title LIKE '%"+search+"%' or author LIKE '%"+search+"%' or year LIKE '%"+search+"%' or genre LIKE '%"+search+"%'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("book_id"); 
                data[totaldata][1] = resultSet.getString("title"); 
                data[totaldata][2] = resultSet.getString("author"); 
                data[totaldata][3] = resultSet.getString("year"); 
                data[totaldata][4] = resultSet.getString("genre"); 
                if(resultSet.getString("available").equals("1")){
                    data[totaldata][5] = "Yes";
                }
                else{
                    data[totaldata][5] = "No";
                }
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfSearch(String search){
        int totaldata=0;
        try{
            String query = "Select book_id,title,author,year,genre,available from book INNER JOIN genre on genre.genre_id=book.genre_id WHERE title LIKE '%"+search+"%' or author LIKE '%"+search+"%' or year LIKE '%"+search+"%' or genre LIKE '%"+search+"%'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public void deleteBook(int id){
        try{
            String query = "DELETE from book WHERE book_id='"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Delete book success!");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateBook(String id, String title, String author, int year, int genre_id) {
        try {
            String query = "UPDATE book SET title='"+title+"', author='"+author+"', year='"+year+"', genre_id='"+genre_id+"' WHERE book_id='"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Book update success!");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
    }
    public void addRent(String[] book, int days, String date, int cost,String username){
        try {
            //java.sql.Date sqlfrom=new java.sql.Date(from.getTime());
            String query = "INSERT INTO rent VALUES (NULL, '"+username+"','"+book[0]+"','"+date+"',DATE_ADD('"+date+"',INTERVAL "+days+" DAY),NULL,"+cost+",0,'Not Returned')";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            String query1 = "UPDATE book SET available="+0+" WHERE book_id="+book[0];
            statement = connection.createStatement();
            statement.executeUpdate(query1);
            JOptionPane.showMessageDialog(null,"Rent book success!");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
    }
    public String[][] readUserRent(String username){
        String[][] data=new String[numOfUserRent(username)][10];
        try{
            int totaldata = 0;
            String query = "Select title,author,year,genre,rent_from,rent_due,return_date,rent_cost,fine,status from rent INNER JOIN book on book.book_id=rent.book_id INNER JOIN genre on genre.genre_id=book.genre_id WHERE username='"+username+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("title"); 
                data[totaldata][1] = resultSet.getString("author"); 
                data[totaldata][2] = resultSet.getString("year"); 
                data[totaldata][3] = resultSet.getString("genre"); 
                data[totaldata][4] = resultSet.getString("rent_from"); 
                data[totaldata][5] = resultSet.getString("rent_due"); 
                data[totaldata][6] = resultSet.getString("return_date"); 
                data[totaldata][7] = resultSet.getString("rent_cost"); 
                data[totaldata][8] = resultSet.getString("fine"); 
                data[totaldata][9] = resultSet.getString("status");
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfUserRent(String username){
        int totaldata=0;
        try{
            String query = "Select rent_id,book.book_id,title,author,year,genre,rent_from,rent_due,return_date,status from rent INNER JOIN book on book.book_id=rent.book_id INNER JOIN genre on genre.genre_id=book.genre_id WHERE username='"+username+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public String[][] readAllRent(){
        String[][] data=new String[numOfAllRent()][13];
        try{
            int totaldata = 0;
            String query = "Select rent_id,book.book_id,username,title,author,year,genre,rent_from,rent_due,return_date,rent_cost,fine,status from rent INNER JOIN book on book.book_id=rent.book_id INNER JOIN genre on genre.genre_id=book.genre_id";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("rent_id"); 
                data[totaldata][1] = resultSet.getString("book_id");
                data[totaldata][2] = resultSet.getString("username");
                data[totaldata][3] = resultSet.getString("title"); 
                data[totaldata][4] = resultSet.getString("author"); 
                data[totaldata][5] = resultSet.getString("year"); 
                data[totaldata][6] = resultSet.getString("genre"); 
                data[totaldata][7] = resultSet.getString("rent_from"); 
                data[totaldata][8] = resultSet.getString("rent_due"); 
                data[totaldata][9] = resultSet.getString("return_date"); 
                data[totaldata][10] = resultSet.getString("rent_cost"); 
                data[totaldata][11] = resultSet.getString("fine"); 
                data[totaldata][12] = resultSet.getString("status");
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfAllRent(){
        int totaldata=0;
        try{
            String query = "Select * from rent";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public String[][] readFilteredRent(String status){
        String[][] data=new String[numOfFilteredRent(status)][13];
        try{
            int totaldata = 0;
            String query = "Select rent_id,book.book_id,username,title,author,year,genre,rent_from,rent_due,return_date,rent_cost,fine,status from rent INNER JOIN book on book.book_id=rent.book_id INNER JOIN genre on genre.genre_id=book.genre_id WHERE status='"+status+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("rent_id"); 
                data[totaldata][1] = resultSet.getString("book_id");
                data[totaldata][2] = resultSet.getString("username");
                data[totaldata][3] = resultSet.getString("title"); 
                data[totaldata][4] = resultSet.getString("author"); 
                data[totaldata][5] = resultSet.getString("year"); 
                data[totaldata][6] = resultSet.getString("genre"); 
                data[totaldata][7] = resultSet.getString("rent_from"); 
                data[totaldata][8] = resultSet.getString("rent_due"); 
                data[totaldata][9] = resultSet.getString("return_date"); 
                data[totaldata][10] = resultSet.getString("rent_cost"); 
                data[totaldata][11] = resultSet.getString("fine"); 
                data[totaldata][12] = resultSet.getString("status");
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfFilteredRent(String status){
        int totaldata=0;
        try{
            String query = "Select * from rent WHERE status='"+status+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
    public void updateRent(int rent_id, int book_id, int days, int fine) {
        try {
            //java.sql.Date sqlfrom=new java.sql.Date(from.getTime());
            String query="UPDATE rent set return_date=DATE_ADD(rent_due,INTERVAL "+days+" DAY),fine="+fine+",status='Returned' WHERE rent_id="+rent_id;
            statement = connection.createStatement();
            statement.executeUpdate(query);
            String query1 = "UPDATE book SET available="+1+" WHERE book_id="+book_id;
            statement = connection.createStatement();
            statement.executeUpdate(query1);
            JOptionPane.showMessageDialog(null,"Return book success!");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
    }
    public boolean checkAvailable(int book_id, String date){
        try {
            String query="SELECT COUNT(*) as total from rent WHERE book_id='"+book_id+"' AND ('"+date+"' between rent_from and return_date OR '"+date+"' < rent_from)";
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(query);
            while(rs.next()){
                if(rs.getString("total").equals("0")){
                    return true;
                }
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
    public String[][] readFilteredRentUser(String status,String user){
        String[][] data=new String[numOfFilteredRentUser(status,user)][13];
        try{
            int totaldata = 0;
            String query = "Select rent_id,book.book_id,username,title,author,year,genre,rent_from,rent_due,return_date,rent_cost,fine,status from rent INNER JOIN book on book.book_id=rent.book_id INNER JOIN genre on genre.genre_id=book.genre_id WHERE status='"+status+"' AND username='"+user+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){ //konversi tabel ke string
                data[totaldata][0] = resultSet.getString("rent_id"); 
                data[totaldata][1] = resultSet.getString("book_id");
                data[totaldata][2] = resultSet.getString("username");
                data[totaldata][3] = resultSet.getString("title"); 
                data[totaldata][4] = resultSet.getString("author"); 
                data[totaldata][5] = resultSet.getString("year"); 
                data[totaldata][6] = resultSet.getString("genre"); 
                data[totaldata][7] = resultSet.getString("rent_from"); 
                data[totaldata][8] = resultSet.getString("rent_due"); 
                data[totaldata][9] = resultSet.getString("return_date"); 
                data[totaldata][10] = resultSet.getString("rent_cost"); 
                data[totaldata][11] = resultSet.getString("fine"); 
                data[totaldata][12] = resultSet.getString("status");
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }
    public int numOfFilteredRentUser(String status, String user){
        int totaldata=0;
        try{
            String query = "Select * from rent WHERE status='"+status+"' AND username='"+user+"'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            while(resultSet.next()){
                totaldata++; 
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totaldata;
    }
}
