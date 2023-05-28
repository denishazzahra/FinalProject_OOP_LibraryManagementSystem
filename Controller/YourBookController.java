/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.MainMenuUserView;
import View.YourBookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class YourBookController {
    LibraryModel LM;
    YourBookView YBV;
    String username;
    String[][] rentdata;
    public YourBookController(LibraryModel LM, YourBookView YBV, String username) {
        this.LM = LM;
        this.YBV = YBV;
        this.username = username;
        showRent();
        YBV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MainMenuUserView MMUV=new MainMenuUserView();
                MainMenuUserController MMUC=new MainMenuUserController(MMUV,LM,username);
                MMUV.setVisible(true);
                YBV.dispose();
            }
        });
        YBV.btnshowall.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showRent();
                YBV.filter.clearSelection();
            }
        });
        YBV.rbreturned.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(YBV.rbreturned.isSelected()){
                    showFilteredRent("Returned");
                }
            }
        });
        YBV.rbnotreturned.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(YBV.rbnotreturned.isSelected()){
                    showFilteredRent("Not Returned");
                }
            }
        });
    }
    void showRent(){
        rentdata=LM.readUserRent(username);
        String[] columnname={"Title","Author","Year","Genre","Rent From","Rent Due","Return Date","Rent Cost","Fine","Status"};
        DefaultTableModel tableModel = new DefaultTableModel(rentdata, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        YBV.tablerent.setModel(tableModel);
    }
    void showFilteredRent(String status){
        rentdata=LM.readFilteredRentUser(status,username);
        String[] columnname={"Title","Author","Year","Genre","Rent From","Rent Due","Return Date","Rent Cost","Fine","Status"};
        String[][] filtered=new String[LM.numOfFilteredRentUser(status, username)][10];
        for(int i=0;i<LM.numOfFilteredRentUser(status, username);i++){
            for(int j=0;j<10;j++){
                filtered[i][j]=rentdata[i][j+3];
            }
        }
        DefaultTableModel tableModel = new DefaultTableModel(filtered, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        YBV.tablerent.setModel(tableModel);
    }
}
