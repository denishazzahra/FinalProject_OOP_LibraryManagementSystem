/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LibraryModel;
import View.BookListView;
import View.EditBookView;
import View.MainMenuAdminView;
import java.awt.event.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
class BookListController {
    BookListView BLV;
    LibraryModel LM;
    String[][] genredata;
    String[][] bookdata;
    int genre_id;
    public BookListController(BookListView BLV, LibraryModel LM) {
        this.BLV = BLV;
        this.LM = LM;
        showGenre();
        showBook();
        BLV.cbgenre.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED)
                {
                    genre_id=Integer.parseInt(genredata[BLV.cbgenre.getSelectedIndex()][0]);
                }
            }
        });
        BLV.btninsert.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!(BLV.getATitle().equals("")||BLV.getAuthor().equals("")||BLV.getYear().equals("")||BLV.cbgenre.getSelectedIndex()==-1)){
                    LM.addBook(BLV.getATitle(),BLV.getAuthor(),Integer.parseInt(BLV.getYear()),genre_id);
                    showBook();
                    resetForm();
                }
            }   
        });
        BLV.tablebook.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                int row = BLV.tablebook.getSelectedRow();
                String id = bookdata[row][0];
                String booktitle=bookdata[row][1];
                int input = JOptionPane.showConfirmDialog(null,
                        "Delete book '"+booktitle+"'?",
                        "Option",
                        JOptionPane.YES_NO_OPTION); // yes =0, no=1
                
                if(input==0){
                    LM.deleteBook(Integer.parseInt(id));
                    showBook();
                }
                else{
                    int input1 = JOptionPane.showConfirmDialog(null,
                        "Edit book '"+ booktitle + "'?",
                        "Option",
                        JOptionPane.YES_NO_OPTION); // yes =0, n0=1
                
                    if(input1==0){
                        EditBookView EBV=new EditBookView();
                        EditBookController EBC=new EditBookController(LM,EBV,bookdata[row]);
                        EBV.setVisible(true);
                        BLV.dispose();
                    }
                }
            }
        });
        BLV.btnback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MainMenuAdminView MMAV=new MainMenuAdminView();
                MainMenuAdminController MMAC=new MainMenuAdminController(MMAV,LM);
                MMAV.setVisible(true);
                BLV.dispose();
            }
        });
    }
    void showGenre(){
        genredata=LM.readGenre();
        String[] genre=new String[LM.numOfGenre()];
        for(int i=0;i<LM.numOfGenre();i++){
            genre[i]=genredata[i][1];
        }
        BLV.cbgenre.setModel(new DefaultComboBoxModel(genre));
        BLV.cbgenre.setSelectedIndex(-1);
    }
    void showBook(){
        bookdata=LM.readBook();
        String[][] sb=new String[LM.numOfBook()][5];
        for(int i=0;i<LM.numOfBook();i++){
            for(int j=0;j<5;j++){
                sb[i][j]=bookdata[i][j+1];
            } 
        }
        String[] columnname={"Title","Author","Year","Genre","Available"};
        DefaultTableModel tableModel = new DefaultTableModel(sb, columnname) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        BLV.tablebook.setModel(tableModel);
    }
    void resetForm(){
        BLV.tftitle.setText("");
        BLV.tfauthor.setText("");
        BLV.tfyear.setText("");
        BLV.cbgenre.setSelectedIndex(-1);
    }
}
