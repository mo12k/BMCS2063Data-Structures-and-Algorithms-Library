/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import dao.BookDAO;
import utility.MessageUI;

/**
 *
 * @author Mok
 */
public class BookMaintenance {
    private ListInterface<Book> bookList = new ArrayList<>();
    private BookDAO bookDAO = new BookDAO();

    public BookMaintenance() {
        bookList = bookDAO.retriveFromFile();
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
    }

    public Book addNewBook(String title, String author, String category, int yearPublished, boolean isAvailable) {
        String nextId = generateNextBookId();
        Book newBook = new Book(nextId, title, author, category, yearPublished, isAvailable);
        bookList.add(newBook);
        bookDAO.saveToFile(bookList);
        return newBook;
    }

    public boolean updateBook(String bookId, String title, String author, String category, int yearPublished, boolean isAvailable) {
        int index = findBookIndexById(bookId);
        if (index == -1) {
            return false;
        }

        Book updated = new Book(bookId, title, author, category, yearPublished, isAvailable);
        bookList.replace(index + 1, updated);
        bookDAO.saveToFile(bookList);
        return true;
    }

    public Book removeBook(String bookId) {
        int index = findBookIndexById(bookId);
        if (index == -1) {
            return null;
        }

        Book removed = bookList.remove(index + 1);
        bookDAO.saveToFile(bookList);
        return removed;
    }

    public Book searchBookById(String bookId) {
        int index = findBookIndexById(bookId);
        if (index == -1) {
            return null;
        }
        return bookList.getEntry(index + 1);
    }

    public String searchBooksByTitle(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        String trimmed = keyword.trim();
        if (trimmed.isEmpty()) {
            return "No keyword entered.";
        }

        StringBuilder sb = new StringBuilder();
        int matchCount = 0;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && book.getTitle() != null
                    && book.getTitle().toLowerCase().contains(trimmed.toLowerCase())) {
                matchCount++;
                sb.append(formatBookDetails(book)).append("\n");
            }
        }

        if (matchCount == 0) {
            return "No books found for title keyword: " + trimmed;
        }

        return sb.toString();
    }

    public String getAllBooksDisplay() {
        if (bookList.isEmpty()) {
            return "No books found.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null) {
                sb.append(formatBookDetails(book)).append("\n");
            }
        }
        return sb.toString();
    }

    private int findBookIndexById(String bookId) {
        if (bookId == null) {
            return -1;
        }
        String target = bookId.trim();
        if (target.isEmpty()) {
            return -1;
        }

        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && target.equalsIgnoreCase(book.getBookID())) {
                return i - 1;
            }
        }
        return -1;
    }

    private String generateNextBookId() {
        int max = 0;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book == null || book.getBookID() == null) {
                continue;
            }
            String id = book.getBookID().trim();
            if (id.length() < 2) {
                continue;
            }
            // Expected format: B0001, B0002, ...
            String digits = id.substring(1);
            try {
                int value = Integer.parseInt(digits);
                if (value > max) {
                    max = value;
                }
            } catch (NumberFormatException ex) {
                // ignore non-standard ids
            }
        }
        int next = max + 1;
        return String.format("B%04d", next);
    }

    public static String formatBookDetails(Book book) {
        if (book == null) {
            return "(null book)";
        }
        return "Book Details\n"
                + "Book ID       : " + safe(book.getBookID()) + "\n"
                + "Title         : " + safe(book.getTitle()) + "\n"
                + "Author        : " + safe(book.getAuthor()) + "\n"
                + "Category      : " + safe(book.getCategory()) + "\n"
                + "Year Published: " + book.getYearPublished() + "\n"
                + "Available     : " + (book.isIsAvailable() ? "Yes" : "No") + "\n";
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
