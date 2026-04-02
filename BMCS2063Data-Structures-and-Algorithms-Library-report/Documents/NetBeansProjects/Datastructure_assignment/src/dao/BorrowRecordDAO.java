/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.BorrowRecord;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author user
 */
public class BorrowRecordDAO {
   private String filename = "BorrowRecord.dat";

    public void saveToFile(ListInterface<BorrowRecord> recordList)  {
        File file = new File(filename);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(recordList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<BorrowRecord> retrieveFromFile() {
        File file = new File(filename);
        ListInterface<BorrowRecord> recordList = new DoublyLinkedList<>();

        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            recordList = (ListInterface<BorrowRecord>) oiStream.readObject();
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found (first run will create new file)");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        }

        return recordList;
    }
}
