/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.LoginUserView;
import View.RegisterUserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
class RegisterUserController {
    RegisterUserView RUV;
    LibraryModel LM;
    public RegisterUserController(RegisterUserView RUV, LibraryModel LM) {
        this.RUV = RUV;
        this.LM = LM;
        RUV.btnsignup.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(RUV.getUsername().equals("")||RUV.getPassword().equals("")||RUV.getConfirm().equals("")){
                    JOptionPane.showMessageDialog(null, "Username/password can't be empty!");
                } else if(LM.usernameCheck(RUV.getUsername())){
                    if(RUV.getPassword().equals(RUV.getConfirm())){
                        LM.registerUser(RUV.getUsername(), RUV.getPassword());
                        RUV.tfusername.setText("");
                        RUV.tfpassword.setText("");
                        RUV.tfconfirm.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Password doesn't match!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Username is not available!");
                }
            }
            
        });
        RUV.btnlogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LoginUserView LUV=new LoginUserView();
                LoginUserController LUC=new LoginUserController(LUV,LM);
                LUV.setVisible(true);
                RUV.dispose();
            }   
        });
    }
}
