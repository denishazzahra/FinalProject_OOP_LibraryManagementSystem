/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.LoginAdminView;
import View.LoginUserView;
import View.MainMenuUserView;
import View.RegisterUserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class LoginUserController {
    LoginUserView LUV;
    LibraryModel LM;
    public LoginUserController(LoginUserView LUV,LibraryModel LM) {
        this.LUV=LUV;
        this.LM=LM;
        LUV.btnlogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(LM.loginUser(LUV.getUsername(), LUV.getPassword())){
                    MainMenuUserView MMUV=new MainMenuUserView();
                    MainMenuUserController MMUC=new MainMenuUserController(MMUV,LM,LUV.getUsername());
                    MMUV.setVisible(true);
                    LUV.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect username/password!");
                }
            }
        });
        LUV.btnadmin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LoginAdminView LAV=new LoginAdminView();
                LoginAdminController LAC=new LoginAdminController(LAV,LM);
                LAV.setVisible(true);
                LUV.dispose();
            }
        });
        LUV.btnsignup.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                RegisterUserView RUV=new RegisterUserView();
                RegisterUserController LAC=new RegisterUserController(RUV,LM);
                RUV.setVisible(true);
                LUV.dispose();
            }
        });
    } 
}
