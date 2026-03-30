/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import adt.*;
import dao.*;
import entity.*;
import java.time.LocalDate;


/**
 *
 * @author user
 */
public class BorrowReturnBook {
    private ListInterface<Book> bookList = new DoublyLinkedList<>();
    private ListInterface<BorrowRecord> borrowRecordList = new DoublyLinkedList<>();
    private BookDAO bookDAO = new BookDAO();
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    
    
    public BorrowReturnBook() {
            bookList = bookDAO.retrieveFromFile();
            borrowRecordList = borrowRecordDAO.retrieveFromFile();
        }
    
    public Book findBookById(String bookId) {
            if (bookId == null || bookId.trim().isEmpty()) {
                return null;
            }

            for (int i = 1; i <= bookList.size(); i++) {
                Book book = bookList.get(i);
                if (book != null && book.getBookID() != null
                        && book.getBookID().equalsIgnoreCase(bookId.trim())) {
                    return book;
                }
            }
            return null;
        }
    

        public boolean returnBook(String studentId, String bookId) {

            if (!isValidStudentId(studentId)) {
                return false;
            }

            BorrowRecord record = findActiveBorrowRecord(studentId, bookId);
            Book book = findBookById(bookId);

            if (record == null || book == null) {
                return false;
            }

            record.setReturnDate(LocalDate.now().toString());
            record.setStatus("RETURNED");

            book.setQuantity(book.getQuantity() + 1);
            book.setIsAvailable(true);

            bookDAO.saveToFile(bookList);
            borrowRecordDAO.saveToFile(borrowRecordList);

            return true;
        }

        public BorrowRecord findActiveBorrowRecord(String studentId, String bookId) {

            for (int i = 1; i <= borrowRecordList.size(); i++) {
                BorrowRecord record = borrowRecordList.get(i);

                if (record != null
                        && record.getBorrowerID()!= null
                        && record.getBookID() != null
                        && record.getStatus() != null
                        && record.getBorrowerID().equalsIgnoreCase(studentId.trim())
                        && record.getBookID().equalsIgnoreCase(bookId.trim())
                        && record.getStatus().equalsIgnoreCase("BORROWED")) {

                    return record;
                }
            }

            return null;
        }

        public boolean checkAvailability(String bookId) {
        Book book = findBookById(bookId);
        return book != null && book.getQuantity() > 0 && book.isIsAvailable();
    }

       public boolean borrowBook(String studentId, String bookId) {

            if (!isValidStudentId(studentId)) {
                return false;
            }

            Book book = findBookById(bookId);

            if (book == null) {
                return false;
            }

            if (!checkAvailability(bookId)) {
                return false;
            }

            if (findActiveBorrowRecord(studentId, bookId) != null) {
                return false;
            }

            book.setQuantity(book.getQuantity() - 1);
            book.setIsAvailable(book.getQuantity() > 0);

            BorrowRecord newRecord = new BorrowRecord(
                    studentId,
                    book.getBookID()
            );

            borrowRecordList.add(newRecord);

            bookDAO.saveToFile(bookList);
            borrowRecordDAO.saveToFile(borrowRecordList);

            return true;
        }

      

    public ListInterface<BorrowRecord> getBorrowedBooks() {
        ListInterface<BorrowRecord> borrowedList = new DoublyLinkedList<>();

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord record = borrowRecordList.get(i);

            if (record != null && record.getStatus() != null
                    && record.getStatus().equalsIgnoreCase("BORROWED")) {
                borrowedList.add(record);
            }
        }

        return borrowedList;
    }
        public String getAllBorrowedBooks() {
        ListInterface<BorrowRecord> borrowedList = getBorrowedBooks();
        String outputStr = "";

        for (int i = 1; i <= borrowedList.size(); i++) {
            outputStr += borrowedList.get(i) + "\n";
        }

        return outputStr;
    }
        
    public String searchBook(String keyword) {
        StringBuilder output = new StringBuilder();

        if (keyword == null) keyword = "";

        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);

            if (book != null) {
                String title = book.getTitle() == null ? "" : book.getTitle();
                String author = book.getAuthor() == null ? "" : book.getAuthor();
                String category = book.getCategory() == null ? "" : book.getCategory();

                if (title.toLowerCase().contains(keyword.toLowerCase())
                        || author.toLowerCase().contains(keyword.toLowerCase())
                        || category.toLowerCase().contains(keyword.toLowerCase())) {

                    output.append(book).append("\n");
                }
            }
        }

        return output.toString();
    }
        
    public ListInterface<BorrowRecord> getAllRecords() {
        return borrowRecordList;
    }
    
    public String getAllRecordString() {
    String outputStr = "";

    for (int i = 1; i <= borrowRecordList.size(); i++) {
        outputStr += borrowRecordList.get(i) + "\n";
    }

    return outputStr;
}
    
    public void refreshExpiredStatus() {
    boolean updated = false;
    LocalDate today = LocalDate.now();

    for (int i = 1; i <= borrowRecordList.size(); i++) {
        BorrowRecord record = borrowRecordList.get(i);

        if (record != null
                && record.getStatus() != null
                && record.getStatus().equalsIgnoreCase("BORROWED")
                && record.getExpiryDate() != null) {

            LocalDate expiryDate = LocalDate.parse(record.getExpiryDate());

            if (today.isAfter(expiryDate)) {
                record.setStatus("EXPIRED");
                updated = true;
            }
        }
    }

    if (updated) {
        borrowRecordDAO.saveToFile(borrowRecordList);
    }
}
    public boolean isValidStudentId(String studentId) {
    return studentId != null && studentId.matches("ST\\d{3,}");
}

}
