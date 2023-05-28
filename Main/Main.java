/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.LoginUserController;
import Model.LibraryModel;
import View.LoginUserView;

/**
 *
 * @author LENOVO
 */
public class Main {
    public static void main(String[] args) {
        LoginUserView LUV=new LoginUserView();
        LibraryModel LM=new LibraryModel();
        LoginUserController LUC=new LoginUserController(LUV,LM);
        LUV.setVisible(true);
    }
}
