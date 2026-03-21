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
        System.out.print("Enter choice: ");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("");
        return choice;
    }
    
    public int getStudentMenu(){
        System.out.println("Student Menu");
        System.out.println("1.Search book");
        System.out.println("2.Display all books");
        System.out.println("0.Quit");
        System.out.print("Enter choice: ");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("");
        return choice;
    }
    
    public void listAllBooks (String outputStr){
        System.out.println("\nList of Books : \n "+ outputStr);
    }
    
    public void printBookDetails(Book book){
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.println("Book Details");
        System.out.println("Book ID       : " + book.getBookID());
        System.out.println("Title         : " + book.getTitle());
        System.out.println("Author        : " + book.getAuthor());
        System.out.println("Category      : " + book.getCategory());
        System.out.println("Year Published: " + book.getYearPublished());
        System.out.println("Available     : " + (book.isIsAvailable() ? "Yes" : "No"));
    }
    
    public String inputBookName(){
        System.out.print("Enter book title keyword: ");
        return scan.nextLine();
    }

    public String inputBookId() {
        System.out.print("Enter Book ID: ");
        return scan.nextLine();
    }

    public String inputBookTitle() {
        System.out.print("Enter title: ");
        return scan.nextLine();
    }

    public String inputBookAuthor() {
        System.out.print("Enter author: ");
        return scan.nextLine();
    }

    public String inputBookCategory() {
        System.out.print("Enter category: ");
        return scan.nextLine();
    }

    public int inputYearPublished() {
        while (true) {
            System.out.print("Enter year published: ");
            String line = scan.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid year. Please enter a number.");
            }
        }
    }

    public boolean inputAvailability() {
        while (true) {
            System.out.print("Is available? (Y/N): ");
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Invalid input. Enter Y or N.");
        }
    }

    public boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Invalid input. Enter Y or N.");
        }
    }
}
