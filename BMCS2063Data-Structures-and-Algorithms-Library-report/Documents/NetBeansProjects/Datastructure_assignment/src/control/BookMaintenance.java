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
        
        // Initialize with sample data if list is empty
        if (bookList.isEmpty()) {
            initializeSampleBooks();
        }
        
        syncNextBookIdFromLoadedData();
    }
    
    private void initializeSampleBooks() {
        // Add some novels and other books
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Novel", 1925, 5));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", "Novel", 1960, 3));
        bookList.add(new Book("1984", "George Orwell", "Novel", 1949, 4));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", "Novel", 1813, 6));
        bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", "Novel", 1951, 2));
        bookList.add(new Book("Brave New World", "Aldous Huxley", "Novel", 1932, 3));
        bookList.add(new Book("Java Programming", "Bill Joy", "Reference", 2015, 5));
        bookList.add(new Book("Effective Java", "Joshua Bloch", "Programming", 2018, 4));
        
        bookDAO.saveToFile(bookList);
    }

    private void syncNextBookIdFromLoadedData() {
        int maxNumericId = 0;
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
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
                        for (int i = 1; i <= results.size(); i++) {
                            outputStr += results.get(i) + "\n";
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
                        for (int i = 1; i <= results.size(); i++) {
                            outputStr += results.get(i) + "\n";
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

        // Use ADT contains() for exact-ID search first.
        if (!searchLower.isEmpty() && searchLower.matches("b\\d+")) {
            Book probe = new Book();
            probe.setBookID(searchLower.toUpperCase());
            if (bookList.contains(probe)) {
                Book exactBook = findBookById(searchLower);
                if (exactBook != null) {
                    matchingBooks.add(exactBook);
                    return matchingBooks;
                }
            }
        }

        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book == null) {
                continue;
            }
            boolean matchesId = book.getBookID() != null && book.getBookID().toLowerCase().contains(searchLower);
            boolean matchesTitle = book.getTitle() != null && book.getTitle().toLowerCase().contains(searchLower);
            boolean matchesAuthor = book.getAuthor() != null && book.getAuthor().toLowerCase().contains(searchLower);
            if ((matchesId || matchesTitle || matchesAuthor) && !matchingBooks.contains(book)) {
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

        Book book = bookList.get(position);
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

        bookList.set(position, book);
        bookDAO.saveToFile(bookList);
        System.out.println("Book updated.");
    }

    private void removeBook() {
        String bookId = bookUI.inputBookId();
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        bookUI.printBookDetails(book);
        if (!bookUI.confirm("Remove this book?") ) {
            return;
        }

        if (bookList.remove(book)) {
            bookDAO.saveToFile(bookList);
            System.out.println("Book removed.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private Book findBookById(String bookId) {
        if (bookId == null) {
            return null;
        }

        String needle = bookId.trim();
        if (needle.isEmpty()) {
            return null;
        }

        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book != null && book.getBookID() != null && book.getBookID().equalsIgnoreCase(needle)) {
                return book;
            }
        }
        return null;
    }

    private int findBookPositionById(String bookId) {
        if (bookId == null) {
            return -1;
        }
        String needle = bookId.trim();
        if (needle.isEmpty()) {
            return -1;
        }
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book != null && book.getBookID() != null && book.getBookID().equalsIgnoreCase(needle)) {
                return i;
            }
        }
        return -1;
    }
    
    public String getAllBooks() {
      String outputStr = "";
      for (int i = 1; i <= bookList.size(); i++) {
        outputStr += bookList.get(i) + "\n";
      }
      return outputStr;
    }
    
    public void displayBooks(){
        reloadData();
        bookUI.listAllBooks(getAllBooks());
    }
    
    private void reloadData() {
    bookList = bookDAO.retrieveFromFile();
    }
    
    public static void main(String[] args){
        BookMaintenance bookMaintenance = new BookMaintenance();
        bookMaintenance.startRunLibrary();
    }
}