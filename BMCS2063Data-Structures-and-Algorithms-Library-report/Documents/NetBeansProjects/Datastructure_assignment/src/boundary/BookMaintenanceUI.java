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
public class BookMaintenanceUI {
    Scanner scan = new Scanner(System.in);

    private int readMenuChoice(String menuTitle) {
        while (true) {
            System.out.print("Enter choice: ");
            String line = scan.nextLine();
            try {
                int choice = Integer.parseInt(line.trim());
                System.out.println("");
                return choice;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for " + menuTitle + ". Please enter a number.");
            }
        }
    }
    
    public int getMainMenuChoice(){
        System.out.println("\nMAIN MENU");
        System.out.println("1.Staff");
        System.out.println("2.Student");
        System.out.println("0.Quit");
        return readMenuChoice("MAIN MENU");
    }
    
    public int getStaffMenu(){
        System.out.println("\n==================================================");
        System.out.println("              Staff Maintaince             ");
        System.out.println("==================================================");
        System.out.println("1.Add new book");
        System.out.println("2.Update book details");
        System.out.println("3.Remove book");
        System.out.println("4.Search book");
        System.out.println("5.Display all books");
        System.out.println("6.Book maintenance report");
        System.out.println("0.Quit");
        return readMenuChoice("Staff Menu");
    }
    
    public int getStudentMenu(){
        System.out.println("\n==================================================");
        System.out.println("               Books              ");
        System.out.println("==================================================");
        System.out.println("1.Search book");
        System.out.println("2.Display all books");
        System.out.println("0.Quit");
        return readMenuChoice("Student Menu");
    }
    
    public void listAllBooks (String outputStr){
         System.out.println("\n==================================================");
        System.out.println("               List of Books             ");
        System.out.println("==================================================\n");
        System.out.println( outputStr);
    }

    public void showBookMaintenanceReport(String outputStr) {
        System.out.println("\n==================================================");
        System.out.println("            BOOK MAINTENANCE REPORT               ");
        System.out.println("==================================================\n");
        System.out.println(outputStr);
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
        System.out.println("Quantity      : " + book.getQuantity());
        System.out.println("Available     : " + (book.isIsAvailable() ? "Yes" : "No"));
    }

    public String getSearchInput() {
        System.out.print("Enter Book (ID/Title/Author) to search: ");
        return scan.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public String inputBookName(){
        System.out.print("Enter book name: ");
        String bookName = scan.nextLine();
        return bookName;
    }
    
    public String inputBookId() {
        System.out.print("Enter Book ID: ");
        return scan.nextLine();
}

    public String inputBookTitle() {
        System.out.print("Enter title: ");
        String bookTitle = scan.nextLine();
        return bookTitle;
    }

    public String inputBookAuthor() {
        System.out.print("Enter author: ");
        String authorName = scan.nextLine();
        return authorName;
    }

    public String inputBookCategory() {
        System.out.print("Enter category: ");
        String bookCategory = scan.nextLine();
        return bookCategory;
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
    


    public int inputQuantity() {
        while (true) {
            System.out.print("Enter quantity: ");
            String line = scan.nextLine();
            try {
                int qty = Integer.parseInt(line.trim());
                if (qty < 0) {
                    System.out.println("Quantity cannot be negative.");
                    continue;
                }
                return qty;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
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
    


    public Book inputBookDetails(){
        String title = inputBookTitle();
        String author = inputBookAuthor();
        String category = inputBookCategory();
        int yearPublish = inputYearPublished();
        int quantity = inputQuantity();
        System.out.println("");
        return new Book(title, author, category, yearPublish, quantity);
    }
   
}