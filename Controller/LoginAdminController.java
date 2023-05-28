/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.LoginAdminView;
import View.LoginUserView;
import View.MainMenuAdminView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class LoginAdminController {
    LoginAdminView LAV;
    LibraryModel LM;
    public LoginAdminController(LoginAdminView LAV,LibraryModel LM) {
        this.LAV=LAV;
        this.LM=LM;
        LAV.btnlogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(LM.loginAdmin(LAV.getUsername(), LAV.getPassword())){
                    MainMenuAdminView MMAV=new MainMenuAdminView();
                    MainMenuAdminController MMAC=new MainMenuAdminController(MMAV,LM);
                    MMAV.setVisible(true);
                    LAV.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect username/password!");
                }
            }
        });
        LAV.btnuser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LoginUserView LUV=new LoginUserView();
                LoginUserController LUC=new LoginUserController(LUV,LM);
                LUV.setVisible(true);
                LAV.dispose();
            }
        });
    } 
}
