/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package datastructure_assignment;

import boundary.BookMaintenanceUI;
import control.BookMaintenance;
import entity.Book;
import utility.MessageUI;

/**
 *
 * @author Mok
 */
public class Datastructure_assignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BookMaintenanceUI ui = new BookMaintenanceUI();
        BookMaintenance bookMaintenance = new BookMaintenance();

        int mainChoice;
        do {
            mainChoice = ui.getMainMenuChoice();
            switch (mainChoice) {
                case 1:
                    runStaffMenu(ui, bookMaintenance);
                    break;
                case 2:
                    runStudentMenu(ui, bookMaintenance);
                    break;
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (mainChoice != 0);
    }

    private static void runStaffMenu(BookMaintenanceUI ui, BookMaintenance bookMaintenance) {
        int choice;
        do {
            choice = ui.getStaffMenu();
            switch (choice) {
                case 1: {
                    String title = ui.inputBookTitle();
                    String author = ui.inputBookAuthor();
                    String category = ui.inputBookCategory();
                    int year = ui.inputYearPublished();
                    int quantity = ui.inputQuantity();

                    Book added = bookMaintenance.addNewBook(title, author, category, year, quantity);
                    System.out.println("Book added successfully. (Auto-generated Book ID: " + added.getBookID() + ")");
                    ui.printBookDetails(added);
                    break;
                }
                case 2: {
                    String id = ui.inputBookId();
                    Book existing = bookMaintenance.searchBookById(id);
                    if (existing == null) {
                        System.out.println("Book not found.");
                        break;
                    }
                    ui.printBookDetails(existing);

                    String title = ui.inputBookTitle();
                    String author = ui.inputBookAuthor();
                    String category = ui.inputBookCategory();
                    int year = ui.inputYearPublished();
                    int quantity = ui.inputQuantity();

                    boolean ok = bookMaintenance.updateBook(existing.getBookID(), title, author, category, year, quantity);
                    System.out.println(ok ? "Book updated successfully." : "Update failed.");
                    break;
                }
                case 3: {
                    String id = ui.inputBookId();
                    Book existing = bookMaintenance.searchBookById(id);
                    if (existing == null) {
                        System.out.println("Book not found.");
                        break;
                    }
                    ui.printBookDetails(existing);
                    if (ui.confirm("Confirm remove this book")) {
                        Book removed = bookMaintenance.removeBook(existing.getBookID());
                        System.out.println(removed != null ? "Book removed successfully." : "Remove failed.");
                    } else {
                        System.out.println("Remove cancelled.");
                    }
                    break;
                }
                case 4: {
                    String keyword = ui.inputBookName();
                    System.out.println(bookMaintenance.searchBooksByTitle(keyword));
                    break;
                }
                case 5:
                    ui.listAllBooks(bookMaintenance.getAllBooksDisplay());
                    break;
                case 0:
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }

    private static void runStudentMenu(BookMaintenanceUI ui, BookMaintenance bookMaintenance) {
        int choice;
        do {
            choice = ui.getStudentMenu();
            switch (choice) {
                case 1: {
                    String keyword = ui.inputBookName();
                    System.out.println(bookMaintenance.searchBooksByTitle(keyword));
                    break;
                }
                case 2:
                    ui.listAllBooks(bookMaintenance.getAllBooksDisplay());
                    break;
                case 0:
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (choice != 0);
    }
    
}
