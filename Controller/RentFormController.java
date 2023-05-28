/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import Others.FictionBook;
import Others.NonFictionBook;
import View.BookRentView;
import View.RentFormView;
import View.YourBookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author LENOVO
 */
class RentFormController {
    LibraryModel LM;
    RentFormView RFV;
    String[] data;
    String username;
    String[] fiction={"Adventure","Comedy","Fantasy","Horror","Romance","Sci-Fi"};
    boolean fic;
    NonFictionBook nf;
    FictionBook f;
    int days;
    int cost;
    //String[] nonfiction={"Biography","History","Literature","Religion","Science"};
    public RentFormController(LibraryModel LM, RentFormView RFV, String[] data, String username) {
        this.LM = LM;
        this.RFV = RFV;
        this.data = data;
        this.username = username;
        setForm();
        if(Arrays.asList(fiction).contains(data[4])){
            f=new FictionBook(data[1],data[2],Integer.parseInt(data[3]),data[4]);
            fic=true;
            f.setRentDays(1);
            RFV.lcost.setText(Integer.toString(f.calculateRent()));
        }else{
            nf=new NonFictionBook(data[1],data[2],Integer.parseInt(data[3]),data[4]);
            fic=false;
            nf.setRentDays(1);
            RFV.lcost.setText(Integer.toString(nf.calculateRent()));
        }
        RFV.sduration.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                updateCost();
            }
        });
        RFV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BookRentView BRV=new BookRentView();
                BookRentController BRC=new BookRentController(LM,BRV,username);
                BRV.setVisible(true);
                RFV.dispose();
            }
        });
        RFV.btnsubmit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int input = JOptionPane.showConfirmDialog(null,
                        "Rent book '"+data[1]+"'?",
                        "Option",
                        JOptionPane.YES_NO_OPTION); // yes =0, no=1
                if(input==0){
                    String date=RFV.tfdate.getText();
                    int book_id=Integer.parseInt(data[0]);
                    updateCost();
                    try{
                        java.util.Date inputdate=(java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(date);  
                        java.util.Date thisday=new java.util.Date();
                        String today=new SimpleDateFormat("yyyy-MM-dd").format(thisday);
                        java.util.Date currentdate=(java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(today);
                        if(LM.checkAvailable(book_id, date) && inputdate.compareTo(currentdate)>=0){
                            LM.addRent(data,days, date,cost,username);
                            YourBookView YBV=new YourBookView();
                            YourBookController YBC=new YourBookController(LM,YBV,username);
                            YBV.setVisible(true);
                            RFV.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Sorry, the book is unavailable! Please set another date.");
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null,"Wrong date format!");
                    }
                }
            }
        });
    }
    void setForm(){
        String title=data[1];
        if(data[1].length()>30){
            title=truncateString(data[1],30);
        }
        RFV.ltitle.setText(title);
        RFV.lauthor.setText(data[2]);
        RFV.lyear.setText(data[3]);
        RFV.lgenre.setText(data[4]);
    }
    String truncateString(String text, int length) {
        String[] results = text.split("(?<=\\G.{" + length + "})");
        return results[0]+" ...";
    }
    void updateCost(){
        days=(Integer) RFV.sduration.getValue();
        if(fic){
            f.setRentDays(days);
            cost=f.calculateRent();
            RFV.lcost.setText(Integer.toString(cost));
        }else{
            nf.setRentDays(days);
            cost=nf.calculateRent();
            RFV.lcost.setText(Integer.toString(cost));
        }
    }
}
