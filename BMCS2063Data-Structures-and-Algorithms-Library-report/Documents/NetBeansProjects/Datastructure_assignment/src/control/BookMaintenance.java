/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.BookMaintenanceUI;
import entity.*;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import utility.MessageUI;

/**
 *
 * @author Mok
 */
public class BookMaintenance {
    private static final int TITLE_COL_WIDTH = 45;
    private static final int AUTHOR_COL_WIDTH = 20;
    private static final int CATEGORY_COL_WIDTH = 20;

    private ListInterface<Book> bookList = new DoublyLinkedList<>();
    private BookDAO bookDAO = new BookDAO();
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private BookMaintenanceUI bookUI = new BookMaintenanceUI();
    
    public BookMaintenance(){
        bookList = bookDAO.retrieveFromFile();
        syncNextBookIdFromLoadedData();
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
                    displayBooks();
                    addNewBook();
                    displayBooks();
                }
                case 2 -> {
                    displayBooks();
                    updateBookDetails();
                }
                case 3 -> {
                    displayBooks();
                    removeBook();
                }
                case 4 -> {
                    System.out.print("Enter Book (ID/Title/Author) to search: ");
                    String searchName = bookUI.getSearchInput();
                    ListInterface<Book> results = searchBook(searchName);
                    if (results.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        bookUI.listAllBooks(formatBooksForDisplay(results));
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
                        bookUI.listAllBooks(formatBooksForDisplay(results));
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

        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
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
        reloadData();

        Book newBook = bookUI.inputBookDetails();

        if (bookList.contains(newBook)) {
            System.out.println("Book already exists in the system.");
            return;
        }

        bookList.add(newBook);
        bookDAO.saveToFile(bookList);

        System.out.println("Book added successfully.");
    }

    private void updateBookDetails() {
        reloadData();
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
        reloadData();
        String bookId = bookUI.inputBookId();
        int position = findBookPositionById(bookId);
        if (position == -1) {
            System.out.println("Book not found.");
            return;
        }

        Book book = bookList.get(position);
        if (book != null && hasActiveBorrowRecord(book.getBookID())) {
            System.out.println("Cannot remove book. This book is currently borrowed by a student.");
            return;
        }

        bookUI.printBookDetails(book);
        if (!bookUI.confirm("Remove this book?") ) {
            return;
        }

        bookList.remove(book);
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
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);

            if (book != null && book.getBookID().equalsIgnoreCase(bookId)) {
                return bookList.indexOf(book);
            }
        }

        return -1;
    }
    
    public String getAllBooks() {
            return formatBooksForDisplay(bookList);
    }
    
    public void displayBooks(){
        reloadData();
        bookUI.listAllBooks(getAllBooks());
    }

    private boolean hasActiveBorrowRecord(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            return false;
        }

        ListInterface<BorrowRecord> borrowRecords = borrowRecordDAO.retrieveFromFile();
        String normalizedBookId = bookId.trim();

        for (int i = 1; i <= borrowRecords.size(); i++) {
            BorrowRecord record = borrowRecords.get(i);
            if (record == null || record.getBookID() == null || record.getStatus() == null) {
                continue;
            }

            boolean sameBook = record.getBookID().equalsIgnoreCase(normalizedBookId);
            boolean isActive = record.getStatus().equalsIgnoreCase("BORROWED")
                    || record.getStatus().equalsIgnoreCase("EXPIRED");

            if (sameBook && isActive) {
                return true;
            }
        }

        return false;
    }

    private void reloadData() {
        bookList = bookDAO.retrieveFromFile();
    }

    private String formatBooksForDisplay(ListInterface<Book> books) {
        if (books == null || books.isEmpty()) {
            return "No books found.";
        }

        String header = String.format("%-6s | %-" + TITLE_COL_WIDTH + "s | %-" + AUTHOR_COL_WIDTH + "s | %-" + CATEGORY_COL_WIDTH + "s | %-4s | %-3s | %-9s | %-7s",
                "BookID", "Title", "Author", "Category", "Year", "Qty", "Available", "Waiting");
        String line = "-".repeat(header.length());
        StringBuilder output = new StringBuilder();

        output.append(header).append("\n");
        output.append(line).append("\n");

        for (int i = 1; i <= books.size(); i++) {
            Book book = books.get(i);
            if (book == null) {
                continue;
            }

                output.append(String.format("%-6s | %-" + TITLE_COL_WIDTH + "s | %-" + AUTHOR_COL_WIDTH + "s | %-" + CATEGORY_COL_WIDTH + "s | %-4d | %-3d | %-9s | %-7d",
                    safe(book.getBookID()),
                    safe(book.getTitle()),
                    safe(book.getAuthor()),
                    safe(book.getCategory()),
                    book.getYearPublished(),
                    book.getQuantity(),
                    book.isIsAvailable() ? "Yes" : "No",
                    book.getWaitingListCount()));
            output.append("\n");
        }

        return output.toString();
    }

    private String safe(String text) {
        return text == null ? "" : text;
    }
}