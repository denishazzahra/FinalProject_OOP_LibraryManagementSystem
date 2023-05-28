/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.BookRentView;
import View.LoginUserView;
import View.MainMenuUserView;
import View.YourBookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author LENOVO
 */
class MainMenuUserController {
    MainMenuUserView MMUV;
    LibraryModel LM;
    String username;
    public MainMenuUserController(MainMenuUserView MMUV, LibraryModel LM, String username) {
        this.MMUV = MMUV;
        this.LM = LM;
        this.username=username;
        MMUV.btnlogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LoginUserView LUV=new LoginUserView();
                LoginUserController LUC=new LoginUserController(LUV,LM);
                LUV.setVisible(true);
                MMUV.dispose();
            }   
        });
        MMUV.btnrent.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BookRentView BRV=new BookRentView();
                BookRentController LUC=new BookRentController(LM,BRV,username);
                BRV.setVisible(true);
                MMUV.dispose();
            }   
        });
        MMUV.btnyourbook.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                YourBookView YBV=new YourBookView();
                YourBookController YBC=new YourBookController(LM,YBV,username);
                YBV.setVisible(true);
                MMUV.dispose();
            }   
        });
    }
}
