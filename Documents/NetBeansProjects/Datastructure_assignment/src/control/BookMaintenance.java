/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.BookMaintenanceUI;
import entity.*;
import dao.BookDAO;
import utility.MessageUI;

/**
 *
 * @author Mok
 */
public class BookMaintenance {
    private ListInterface<Book> bookList = new DoublyLinkedList<>();
    private BookDAO bookDAO = new BookDAO();
    private BookMaintenanceUI bookUI = new BookMaintenanceUI();
    
    public BookMaintenance(){
        bookList = bookDAO.retrieveFromFile();
        syncNextBookIdFromLoadedData();
    }

    private void syncNextBookIdFromLoadedData() {
        int maxNumericId = 0;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book == null || book.getBookID() == null) {
                continue;
            }

            String id = book.getBookID().trim();
            if (id.length() < 2 || Character.toUpperCase(id.charAt(0)) != 'B') {
                continue;
            }

            try {
                int numericId = Integer.parseInt(id.substring(1));
                if (numericId > maxNumericId) {
                    maxNumericId = numericId;
                }
            } catch (NumberFormatException ex) {
                // Ignore malformed IDs and continue scanning valid ones.
            }
        }
        Book.setNextBookNumber(maxNumericId + 1);
    }
    
    /**
     * 
     */
    public void startRunLibrary(){
        int mainChoice = 0;
        do{
            mainChoice = bookUI.getMainMenuChoice();
            switch(mainChoice){
                case 0 -> {
                    MessageUI.displayExitMessage();
                    break;
                } 
                case 1 -> {
                    runStaffMenu();
                    break;
                }
                case 2 -> {
                    runStudentMenu();
                    break;
                }
                default-> {
                    MessageUI.displayInvalidChoiceMessage();
                    break;
                }
            }
        }while(mainChoice !=0);
    }
    
    public void runStaffMenu(){
        int choice = 0;
        do {
            choice = bookUI.getStaffMenu();
            switch (choice) {
                case 0 -> MessageUI.displayExitMessage();
                case 1 -> {
                    addNewBook();
                    displayBooks();
                }
                case 2 -> updateBookDetails();
                case 3 -> removeBook();
                case 4 -> {
                    System.out.print("Enter Book (ID/Title/Author) to search: ");
                    String searchName = bookUI.getSearchInput();
                    ListInterface<Book> results = searchBook(searchName);
                    if (results.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        String outputStr = "";
                        for (int i = 1; i <= results.getNumberOfEntries(); i++) {
                            outputStr += results.getEntry(i) + "\n";
                        }
                        bookUI.listAllBooks(outputStr);
                    }
                }
                case 5 -> displayBooks();
                default -> MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void runStudentMenu(){
        int choice = 0;
        do {
            choice = bookUI.getStudentMenu();
            switch (choice) {
                case 0 -> MessageUI.displayExitMessage();
                case 1 -> {
                    System.out.print("Enter Book (ID/Title/Author) to search: ");
                    String searchName = bookUI.getSearchInput();
                    ListInterface<Book> results = searchBook(searchName);
                    if (results.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        String outputStr = "";
                        for (int i = 1; i <= results.getNumberOfEntries(); i++) {
                            outputStr += results.getEntry(i) + "\n";
                        }
                        bookUI.listAllBooks(outputStr);
                    }
                }
                case 2 -> displayBooks();
                default -> MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }
    
    public ListInterface<Book> searchBook(String searchName){
        ListInterface<Book> matchingBooks = new DoublyLinkedList<>();
        String searchLower = (searchName == null) ? "" : searchName.toLowerCase().trim();

        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book == null) {
                continue;
            }
            boolean matchesId = book.getBookID() != null && book.getBookID().toLowerCase().contains(searchLower);
            boolean matchesTitle = book.getTitle() != null && book.getTitle().toLowerCase().contains(searchLower);
            boolean matchesAuthor = book.getAuthor() != null && book.getAuthor().toLowerCase().contains(searchLower);
            if (matchesId || matchesTitle || matchesAuthor) {
                matchingBooks.add(book);
            }
        }

        return matchingBooks;
    }
    
    public void addNewBook(){
        Book newBook = bookUI.inputBookDetails();
        bookList.add(newBook);
        bookDAO.saveToFile(bookList);
    }

    private void updateBookDetails() {
        String bookId = bookUI.inputBookId();
        int position = findBookPositionById(bookId);
        if (position == -1) {
            System.out.println("Book not found.");
            return;
        }

        Book book = bookList.getEntry(position);
        bookUI.printBookDetails(book);
        if (!bookUI.confirm("Update this book?") ) {
            return;
        }

        String title = bookUI.inputBookTitle();
        String author = bookUI.inputBookAuthor();
        String category = bookUI.inputBookCategory();
        int yearPublished = bookUI.inputYearPublished();
        int quantity = bookUI.inputQuantity();
        boolean available = bookUI.inputAvailability();

        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setYearPublished(yearPublished);
        book.setQuantity(quantity);
        book.setIsAvailable(available);

        bookList.replace(position, book);
        bookDAO.saveToFile(bookList);
        System.out.println("Book updated.");
    }

    private void removeBook() {
        String bookId = bookUI.inputBookId();
        int position = findBookPositionById(bookId);
        if (position == -1) {
            System.out.println("Book not found.");
            return;
        }

        Book book = bookList.getEntry(position);
        bookUI.printBookDetails(book);
        if (!bookUI.confirm("Remove this book?") ) {
            return;
        }

        bookList.remove(position);
        bookDAO.saveToFile(bookList);
        System.out.println("Book removed.");
    }

    private int findBookPositionById(String bookId) {
        if (bookId == null) {
            return -1;
        }
        String needle = bookId.trim();
        if (needle.isEmpty()) {
            return -1;
        }
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && book.getBookID() != null && book.getBookID().equalsIgnoreCase(needle)) {
                return i;
            }
        }
        return -1;
    }
    
    public String getAllBooks() {
      String outputStr = "";
      for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
        outputStr += bookList.getEntry(i) + "\n";
      }
      return outputStr;
    }
    
    public void displayBooks(){
        bookUI.listAllBooks(getAllBooks());
    }
    
    public static void main(String[] args){
        BookMaintenance bookMaintenance = new BookMaintenance();
        bookMaintenance.startRunLibrary();
    }
}