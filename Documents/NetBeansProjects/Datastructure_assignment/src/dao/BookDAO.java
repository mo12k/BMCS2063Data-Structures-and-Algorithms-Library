/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author Mok
 */
public class BookDAO {
    private String filename ="Book.dat";
    
    public void saveToFile(ListInterface<Book> bookList){
        File file = new File(filename);
        try{
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(bookList);
            ooStream.close();
        }catch(FileNotFoundException ex){
            System.out.println("\nFIle not found");
        }catch (IOException ex){
            System.out.println("\nCannot save to file");
        }
    }
    
    public ListInterface<Book> retriveFromFile(){
        File file = new File(filename);
        ListInterface<Book> bookList = new ArrayList<>();
        try{
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            bookList = (ArrayList<Book>) (oiStream.readObject());
            oiStream.close();
        }catch (FileNotFoundException ex){
            System.out.println("\nFIle not found");            
        }catch (IOException ex){
            System.out.println("\nCannot read from file.");            
        }catch (ClassNotFoundException ex){
            System.out.println("\nClass not found.");
        }finally{
            return bookList;
        }
    }
}
