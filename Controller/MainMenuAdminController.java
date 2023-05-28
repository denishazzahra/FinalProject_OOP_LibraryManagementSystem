/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.BookListView;
import View.LoginAdminView;
import View.MainMenuAdminView;
import View.RentManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author LENOVO
 */
public class MainMenuAdminController {
    MainMenuAdminView MMAV;
    LibraryModel LM;
    public MainMenuAdminController(MainMenuAdminView MMAV, LibraryModel LM) {
        this.MMAV = MMAV;
        this.LM = LM;
        MMAV.btnbooklist.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BookListView BLV=new BookListView();
                BookListController BLC=new BookListController(BLV,LM);
                BLV.setVisible(true);
                MMAV.dispose();
            }
        });
        MMAV.btnrentlist.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                RentManagementView RMV=new RentManagementView();
                RentManagementController BLC=new RentManagementController(LM,RMV);
                RMV.setVisible(true);
                MMAV.dispose();
            }
        });
        MMAV.btnlogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LoginAdminView LAV=new LoginAdminView();
                LoginAdminController LAC=new LoginAdminController(LAV,LM);
                LAV.setVisible(true);
                MMAV.dispose();
            }
        });
    }
    
}
