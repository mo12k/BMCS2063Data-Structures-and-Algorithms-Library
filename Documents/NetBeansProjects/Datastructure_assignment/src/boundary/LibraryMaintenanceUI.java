/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Book;
import java.util.Scanner;


/**
 *
 * @author Mok
 */
public class LibraryMaintenanceUI {
    Scanner scan = new Scanner(System.in);
    
    public int getMainMenuChoice(){
        System.out.println("\nMAIN MENU");
        System.out.println("1.Staff");
        System.out.println("2.Student");
        System.out.println("0.Quit");
        System.out.print("Enter choice: ");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("");
        return choice;
    }
    
    public int getStaffMenu(){
        System.out.println("Staff Menu");
        System.out.println("1.Add new book");
        System.out.println("2.Update book details");
        System.out.println("3.Remove book");
        System.out.println("4.Search book");
        System.out.println("5.Display all books");
        System.out.println("0.Quit");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("");
        return choice;
    }
    
    public int getStudentMenu(){
        System.out.println("Student Menu");
        System.out.println("1.Search book");
        System.out.println("2.Display al book");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("");
        return choice;
    }
    
    public void listAllBooks (String outputStr){
        System.out.println("\nList of Books : \n "+ outputStr);
    }
    
    public void printBookDetails(Book book){
        System.out.println("Book Details");
        System.out.println("Book ID: " + book.getBookID());
        
    }
    
    public String inputBookName(){
        System.out.print("Enter book name: ");
        String bookName = scan.nextLine();
        return bookName;
    }
    
    public 
}
