/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Yang
 */

import adt.DoublyLinkedList;
import adt.ListInterface;
import entity.Fine;
import java.io.*;

public class FineDAO {
    private String filename = "Fine.dat";

    public void saveToFile(ListInterface<Fine> fineList) {
        File file = new File(filename);
        try (ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file))) {
            ooStream.writeObject(fineList);
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    @SuppressWarnings("unchecked")
    public ListInterface<Fine> retrieveFromFile() {
        File file = new File(filename);
        ListInterface<Fine> fineList = new DoublyLinkedList<>();

        try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file))) {
            Object storedData = oiStream.readObject();
            if (storedData instanceof DoublyLinkedList<?>) {
                fineList = (DoublyLinkedList<Fine>) storedData;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo saved fines found. Starting with empty list.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        }

        return fineList;
    }
}
