/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.ConfirmReturnView;
import View.MainMenuAdminView;
import View.RentManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class RentManagementController {
    LibraryModel LM;
    RentManagementView RMV;
    String[][] rentdata;
    public RentManagementController(LibraryModel LM, RentManagementView RMV) {
        this.LM = LM;
        this.RMV = RMV;
        showData();
        RMV.btnshowall.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showData();
                RMV.filter.clearSelection();
            }
        });
        RMV.rbreturned.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(RMV.rbreturned.isSelected()){
                    showFilteredData("Returned");
                }
            }
        });
        RMV.rbnotreturned.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(RMV.rbnotreturned.isSelected()){
                    showFilteredData("Not Returned");
                }
            }
        });
        RMV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MainMenuAdminView MMAV=new MainMenuAdminView();
                MainMenuAdminController MMAC=new MainMenuAdminController(MMAV,LM);
                MMAV.setVisible(true);
                RMV.dispose();
            }
        });
        RMV.tablerent.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                int row = RMV.tablerent.getSelectedRow();
                if(rentdata[row][12].equals("Not Returned")){
                    ConfirmReturnView CRV=new ConfirmReturnView();
                    ConfirmReturnController CRC=new ConfirmReturnController(LM,CRV,rentdata[row]);
                    CRV.setVisible(true);
                    RMV.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Book is already returned!");
                }
            }
        });
    }
    void showData(){
        rentdata=LM.readAllRent();
        String[][] data=new String[LM.numOfAllRent()][11];
        for(int i=0;i<LM.numOfAllRent();i++){
            for(int j=0;j<11;j++){
                data[i][j]=rentdata[i][j+2];
            }
        }
        String[] columnname={"Username","Title","Author","Year","Genre","Rent From","Rent Due","Return Date","Rent Cost","Fine","Status"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        RMV.tablerent.setModel(tableModel);
    }
    void showFilteredData(String status){
        rentdata=LM.readFilteredRent(status);
        String[][] data=new String[LM.numOfFilteredRent(status)][11];
        for(int i=0;i<LM.numOfFilteredRent(status);i++){
            for(int j=0;j<11;j++){
                data[i][j]=rentdata[i][j+2];
            }
        }
        String[] columnname={"Username","Title","Author","Year","Genre","Rent From","Rent Due","Return Date","Rent Cost","Fine","Status"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        RMV.tablerent.setModel(tableModel);
    }
}
