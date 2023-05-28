/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.BookRentView;
import View.MainMenuUserView;
import View.RentFormView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
class BookRentController {
    LibraryModel LM;
    BookRentView BRV;
    String username;
    String[][] bookdata;
    public BookRentController(LibraryModel LM, BookRentView BRV, String username) {
        this.LM = LM;
        this.BRV = BRV;
        this.username=username;
        showBook();
        BRV.btnsearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showSearch();
            }
        });
        BRV.btnshowall.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showBook();
                BRV.tfsearch.setText("");
            }
        });
        BRV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MainMenuUserView MMUV=new MainMenuUserView();
                MainMenuUserController MMUC=new MainMenuUserController(MMUV,LM,username);
                MMUV.setVisible(true);
                BRV.dispose();
            }
        });
        BRV.tablebook.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                int row = BRV.tablebook.getSelectedRow();
                String id = bookdata[row][0];
                String booktitle=bookdata[row][1];
                if(bookdata[row][5].equals("Yes")){
                    RentFormView RFV=new RentFormView();
                    RentFormController RFC=new RentFormController(LM,RFV,bookdata[row],username);
                    RFV.setVisible(true);
                    BRV.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Book unavailable!");
                }
            }
        });
    }
    void showBook(){
        bookdata=LM.readBook();
        String[][] sb=new String[LM.numOfBook()][5];
        for(int i=0;i<LM.numOfBook();i++){
            for(int j=0;j<5;j++){
                sb[i][j]=bookdata[i][j+1];
            } 
        }
        String[] columnname={"Title","Author","Year","Genre","Available"};
        DefaultTableModel tableModel = new DefaultTableModel(sb, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        BRV.tablebook.setModel(tableModel);
    }
    void showSearch(){
        bookdata=LM.readSearch(BRV.getSearch());
        String[][] sb=new String[LM.numOfSearch(BRV.getSearch())][5];
        for(int i=0;i<LM.numOfSearch(BRV.getSearch());i++){
            for(int j=0;j<5;j++){
                sb[i][j]=bookdata[i][j+1];
            } 
        }
        String[] columnname={"Title","Author","Year","Genre","Available"};
        DefaultTableModel tableModel = new DefaultTableModel(sb, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        BRV.tablebook.setModel(tableModel);
    }
}
