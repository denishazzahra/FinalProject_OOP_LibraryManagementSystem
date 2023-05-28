/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.BookListView;
import View.EditBookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author LENOVO
 */
class EditBookController {
    LibraryModel LM;
    EditBookView EBV;
    String[] book;
    String[][] genredata;
    int genre_id;
    public EditBookController(LibraryModel LM, EditBookView EBV, String[] book) {
        this.LM = LM;
        this.EBV = EBV;
        this.book = book;
        EBV.tftitle.setText(book[1]);
        EBV.tfauthor.setText(book[2]);
        EBV.tfyear.setText(book[3]);
        showGenre();
        EBV.cbgenre.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED)
                {
                    genre_id=Integer.parseInt(genredata[EBV.cbgenre.getSelectedIndex()][0]);
                }
            }
        });
        EBV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BookListView BLV=new BookListView();
                BookListController LUC=new BookListController(BLV,LM);
                BLV.setVisible(true);
                EBV.dispose();
            }
        });
        EBV.btnedit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!(EBV.getATitle().equals("")||EBV.getAuthor().equals("")||EBV.getYear().equals("")||EBV.cbgenre.getSelectedIndex()==-1)){
                    LM.updateBook(book[0],EBV.getATitle(),EBV.getAuthor(),Integer.parseInt(EBV.getYear()),genre_id);
                    BookListView BLV=new BookListView();
                    BookListController LUC=new BookListController(BLV,LM);
                    BLV.setVisible(true);
                    EBV.dispose();
                }
            }   
        });
    }
    void showGenre(){
        genredata=LM.readGenre();
        String[] genre=new String[LM.numOfGenre()];
        for(int i=0;i<LM.numOfGenre();i++){
            genre[i]=genredata[i][1];
        }
        EBV.cbgenre.setModel(new DefaultComboBoxModel(genre));
        EBV.cbgenre.setSelectedItem(book[4]);
        genre_id=Integer.parseInt(genredata[EBV.cbgenre.getSelectedIndex()][0]);
    }
    
}
