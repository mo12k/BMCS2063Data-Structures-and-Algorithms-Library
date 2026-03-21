/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import dao.BookDAO;

/**
 *
 * @author Mok
 */
public class BookMaintenance {
    private ListInterface<Book> bookList = new ArrayList<>();
    private BookDAO bookDAO = new BookDAO();
    
    public BookMaintenance(){
        bookList = bookDAO.retriveFromFile();
        syncNextBookIdCounter();
    }

    private void syncNextBookIdCounter() {
        int max = 0;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book == null || book.getBookID() == null) {
                continue;
            }
            String digitsOnly = book.getBookID().replaceAll("\\D+", "");
            if (digitsOnly.isEmpty()) {
                continue;
            }
            try {
                int value = Integer.parseInt(digitsOnly);
                if (value > max) {
                    max = value;
                }
            } catch (NumberFormatException ex) {
                // ignore non-numeric IDs
            }
        }
        Book.setNextBookNumber(max + 1);
    }

    private String normalizeBookId(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim().toUpperCase();
        if (trimmed.isEmpty()) {
            return null;
        }

        // Accept digits-only input like "1" or "0001" and convert to "B0001".
        if (trimmed.matches("\\d+")) {
            try {
                int value = Integer.parseInt(trimmed);
                return String.format("B%04d", value);
            } catch (NumberFormatException ex) {
                return trimmed;
            }
        }

        // If user types something like "b1" or "B0001" keep it, but pad if possible.
        if (trimmed.matches("B\\d+")) {
            String digits = trimmed.substring(1);
            try {
                int value = Integer.parseInt(digits);
                return String.format("B%04d", value);
            } catch (NumberFormatException ex) {
                return trimmed;
            }
        }

        return trimmed;
    }

    private void persist() {
        bookDAO.saveToFile(bookList);
    }

    public Book addNewBook(String title, String author, String category, int yearPublished, int quantity) {
        Book added = new Book(title, author, category, yearPublished, quantity);
        bookList.add(added);
        persist();
        return added;
    }

    // Backward-compatible overload (if other code still calls the old signature)
    public Book addNewBook(String title, String author, String category, int yearPublished, boolean isAvailable) {
        Book added = new Book(title, author, category, yearPublished, isAvailable);
        bookList.add(added);
        persist();
        return added;
    }

    public Book searchBookById(String bookId) {
        String normalized = normalizeBookId(bookId);
        if (normalized == null) return null;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && normalized.equalsIgnoreCase(book.getBookID())) {
                return book;
            }
        }
        return null;
    }

    public boolean updateBook(String bookId, String title, String author, String category, int yearPublished, int quantity) {
        int position = findPositionById(bookId);
        if (position < 1) {
            return false;
        }
        Book book = bookList.getEntry(position);
        if (book == null) {
            return false;
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setYearPublished(yearPublished);
        book.setQuantity(quantity);
        boolean ok = bookList.replace(position, book);
        if (ok) {
            persist();
        }
        return ok;
    }

    // Backward-compatible overload
    public boolean updateBook(String bookId, String title, String author, String category, int yearPublished, boolean isAvailable) {
        int position = findPositionById(bookId);
        if (position < 1) {
            return false;
        }
        Book book = bookList.getEntry(position);
        if (book == null) {
            return false;
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setYearPublished(yearPublished);
        book.setIsAvailable(isAvailable);
        boolean ok = bookList.replace(position, book);
        if (ok) {
            persist();
        }
        return ok;
    }

    public Book removeBook(String bookId) {
        int position = findPositionById(bookId);
        if (position < 1) {
            return null;
        }
        Book removed = bookList.remove(position);
        if (removed != null) {
            persist();
        }
        return removed;
    }

    public String getAllBooksDisplay() {
        if (bookList.isEmpty()) {
            return "(No books found)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            sb.append(bookList.getEntry(i)).append("\n");
        }
        return sb.toString();
    }

    public String searchBooksByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "Please enter a title keyword.";
        }

        String key = keyword.trim().toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && book.getTitle() != null && book.getTitle().toLowerCase().contains(key)) {
                sb.append(book).append("\n");
            }
        }

        if (sb.length() == 0) {
            return "No matching books.";
        }
        return sb.toString();
    }

    private int findPositionById(String bookId) {
        String normalized = normalizeBookId(bookId);
        if (normalized == null) return -1;
        for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
            Book book = bookList.getEntry(i);
            if (book != null && normalized.equalsIgnoreCase(book.getBookID())) {
                return i;
            }
        }
        return -1;
    }
}