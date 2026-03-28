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
        try(ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file))){
            ooStream.writeObject(bookList);
        }catch(FileNotFoundException ex){
            System.out.println("\nFIle not found");
        }catch (IOException ex){
            System.out.println("\nCannot save to file");
        }
    }
    
    public ListInterface<Book> retrieveFromFile(){
        File file = new File(filename);
        ListInterface<Book> bookList = new DoublyLinkedList<>();
        try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file))) {
            Object storedData = oiStream.readObject();
            if (storedData instanceof ListInterface<?>) {
                ListInterface<?> rawList = (ListInterface<?>) storedData;
                ListInterface<Book> convertedList = new DoublyLinkedList<>();
                for (int i = 1; i <= rawList.getNumberOfEntries(); i++) {
                    convertedList.add((Book) rawList.getEntry(i));
                }
                bookList = convertedList;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        }
        return bookList;
    }
}
