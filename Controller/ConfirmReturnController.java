/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import Others.FictionBook;
import Others.NonFictionBook;
import View.ConfirmReturnView;
import View.RentManagementView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author LENOVO
 */
class ConfirmReturnController {
    LibraryModel LM;
    ConfirmReturnView CRV;
    String[] data;
    String[] fiction={"Adventure","Comedy","Fantasy","Horror","Romance","Sci-Fi"};
    boolean fic;
    NonFictionBook nf;
    FictionBook f;
    int days;
    int fine;
    public ConfirmReturnController(LibraryModel LM, ConfirmReturnView CRV, String[] data) {
        this.LM = LM;
        this.CRV = CRV;
        this.data = data;
        showData();
        if(Arrays.asList(fiction).contains(data[6])){
            f=new FictionBook(data[3],data[4],Integer.parseInt(data[5]),data[6]);
            fic=true;
            f.setLateDays(0);
            f.setRentDays(Integer.parseInt(data[10])/2000);
            CRV.lfine.setText(Integer.toString(f.calculateFine()));
        }else{
            nf=new NonFictionBook(data[3],data[4],Integer.parseInt(data[5]),data[6]);
            fic=false;
            nf.setLateDays(0);
            nf.setRentDays(Integer.parseInt(data[10])/3000);
            CRV.lfine.setText(Integer.toString(nf.calculateFine()));
        }
        CRV.rblate.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(CRV.rblate.isSelected()){
                    SpinnerModel sm = new SpinnerNumberModel(1, 1, 1000, 1);
                    CRV.sdaylate.setModel(sm);
                    CRV.sdaylate.setEnabled(true);
                    updateFine();
                }
            }
        });
        CRV.rbontime.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(CRV.rbontime.isSelected()){
                    SpinnerModel sm = new SpinnerNumberModel(0, 0, 1000, 1);
                    CRV.sdaylate.setModel(sm);
                    CRV.sdaylate.setEnabled(false);
                    updateFine();
                }
            }
        });
        CRV.sdaylate.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                updateFine();
            }
        });
        CRV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                RentManagementView RMV=new RentManagementView();
                RentManagementController RMC=new RentManagementController(LM,RMV);
                RMV.setVisible(true);
                CRV.dispose();
            }
        });
        CRV.btnconfirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(CRV.returntime.getSelection()!=null){
                    int input = JOptionPane.showConfirmDialog(null,
                        "Confirm returning book '"+data[3]+"'?",
                        "Option",
                        JOptionPane.YES_NO_OPTION); // yes =0, no=1
                    if(input==0){
                        updateFine();
                        LM.updateRent(Integer.parseInt(data[0]),Integer.parseInt(data[1]),days,fine);
                        RentManagementView RMV=new RentManagementView();
                        RentManagementController RMC=new RentManagementController(LM,RMV);
                        RMV.setVisible(true);
                        CRV.dispose();
                    }
                }
            }
        });
    }
    void showData(){
        String title=data[3];
        CRV.lusername.setText(data[2]);
        if(data[3].length()>32){
            title=truncateString(data[3],32);
        }
        CRV.lbooktitle.setText(title);
        CRV.lauthor.setText(data[4]);
        CRV.lyear.setText(data[5]);
        CRV.lgenre.setText(data[6]);
        CRV.lrentfrom.setText(data[7]);
        CRV.lrentdue.setText(data[8]);
        CRV.sdaylate.setEnabled(false);
    }
    void updateFine(){
        days=(Integer) CRV.sdaylate.getValue();
        if(fic){
            f.setRentDays(Integer.parseInt(data[10])/2000);
            f.setLateDays(days);
            fine=f.calculateFine();
            CRV.lfine.setText(Integer.toString(fine));
        }else{
            nf.setRentDays(Integer.parseInt(data[10])/3000);
            nf.setLateDays(days);
            fine=nf.calculateFine();
            CRV.lfine.setText(Integer.toString(fine));
        }
    }
    String truncateString(String text, int length) {
        String[] results = text.split("(?<=\\G.{" + length + "})");
        return results[0]+" ...";
    }
}
