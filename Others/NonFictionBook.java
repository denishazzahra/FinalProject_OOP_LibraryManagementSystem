/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Others;

/**
 *
 * @author LENOVO
 */
public class NonFictionBook extends Book{
    private int rentdays;
    private int late;
    public NonFictionBook(String title, String author, int year, String genre) {
        super(title,author,year,genre);
    }
    public void setRentDays(int days){
        this.rentdays=days;
    }
    public int getRentDays(){
        return rentdays;
    }
    // Getters and setters for additional properties
    public void setLateDays(int late){
        this.late=late;
    }
    public int getLateDays(){
        return late;
    }
    @Override
    public int calculateRent() {
        return 3000*rentdays;
    }

    @Override
    public int calculateFine() {
        return late*calculateRent()/10;
    }
}
