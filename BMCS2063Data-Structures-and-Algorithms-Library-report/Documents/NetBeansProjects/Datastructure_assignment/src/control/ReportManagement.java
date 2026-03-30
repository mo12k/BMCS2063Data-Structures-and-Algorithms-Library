/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author Yang
 */

import adt.DoublyLinkedList;
import adt.ListInterface;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import entity.Book;
import entity.BorrowRecord;
import entity.Reservation;
import java.time.LocalDate;

public class ReportManagement {

    private ListInterface<BorrowRecord> borrowRecordList;
    private ListInterface<Book> bookList;
    private ListInterface<Reservation> reservationList;

    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private BookDAO bookDAO = new BookDAO();

    public ReportManagement() {
        borrowRecordList = borrowRecordDAO.retrieveFromFile();
        bookList = bookDAO.retrieveFromFile();
        reservationList = new DoublyLinkedList<>();
    }

    // optional: let other module pass reservation list in
    public void setReservationList(ListInterface<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ListInterface<Reservation> getReservationList() {
        return reservationList;
    }

    // 1. Current Reserve Report
    public String getCurrentReserveReport() {
        String output = "";

        if (reservationList == null || reservationList.isEmpty()) {
            return "No current reservation records.";
        }

        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getStatus() != null
                    && r.getStatus().equalsIgnoreCase("Active")) {
                output += r + "\n";
            }
        }

        return output.isEmpty() ? "No current reservation records." : output;
    }

    // 2. Borrowed Book Report
    public String getBorrowedBooksReport() {
        String output = "";

        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No borrowed books.";
        }

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord r = borrowRecordList.get(i);

            if (r != null
                    && r.getStatus() != null
                    && r.getStatus().equalsIgnoreCase("BORROWED")) {
                output += r + "\n";
            }
        }

        return output.isEmpty() ? "No borrowed books." : output;
    }

    // 3. Overdue Books Report
    public String getOverdueBooksReport() {
        String output = "";
        LocalDate today = LocalDate.now();

        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No overdue books.";
        }

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord r = borrowRecordList.get(i);

            if (r != null
                    && r.getExpiryDate() != null
                    && r.getStatus() != null
                    && !r.getStatus().equalsIgnoreCase("RETURNED")) {

                LocalDate expiryDate = LocalDate.parse(r.getExpiryDate());

                if (today.isAfter(expiryDate)) {
                    long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(expiryDate, today);

                    output += r.getRecordID()
                            + " | Borrower: " + r.getBorrowerID()
                            + " | Book: " + r.getBookID()
                            + " | Expiry Date: " + r.getExpiryDate()
                            + " | Overdue Days: " + overdueDays
                            + " | Status: " + r.getStatus()
                            + "\n";
                }
            }
        }

        return output.isEmpty() ? "No overdue books." : output;
    }

    // 4. Most Borrowed Books Report
    public String getMostBorrowedBooksReport() {
        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No borrowing records found.";
        }

        ListInterface<String> countedBookIDs = new DoublyLinkedList<>();
        int highestCount = 0;
        String output = "";

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord currentRecord = borrowRecordList.get(i);

            if (currentRecord == null || currentRecord.getBookID() == null) {
                continue;
            }

            String currentBookID = currentRecord.getBookID();

            if (countedBookIDs.contains(currentBookID)) {
                continue;
            }

            int count = 0;

            for (int j = 1; j <= borrowRecordList.size(); j++) {
                BorrowRecord compareRecord = borrowRecordList.get(j);

                if (compareRecord != null
                        && compareRecord.getBookID() != null
                        && compareRecord.getBookID().equalsIgnoreCase(currentBookID)) {
                    count++;
                }
            }

            countedBookIDs.add(currentBookID);

            if (count > highestCount) {
                highestCount = count;
            }
        }

        if (highestCount == 0) {
            return "No borrowing records found.";
        }

        output += "Most Borrowed Book(s):\n";

        for (int i = 1; i <= countedBookIDs.size(); i++) {
            String bookID = countedBookIDs.get(i);
            int count = 0;

            for (int j = 1; j <= borrowRecordList.size(); j++) {
                BorrowRecord r = borrowRecordList.get(j);

                if (r != null
                        && r.getBookID() != null
                        && r.getBookID().equalsIgnoreCase(bookID)) {
                    count++;
                }
            }

            if (count == highestCount) {
                Book book = findBookById(bookID);

                if (book != null) {
                    output += book.getBookID()
                            + " | " + book.getTitle()
                            + " | Borrow Count: " + count + "\n";
                } else {
                    output += bookID + " | Borrow Count: " + count + "\n";
                }
            }
        }

        return output;
    }

    private Book findBookById(String bookID) {
        if (bookID == null || bookID.trim().isEmpty()) {
            return null;
        }

        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);

            if (book != null
                    && book.getBookID() != null
                    && book.getBookID().equalsIgnoreCase(bookID.trim())) {
                return book;
            }
        }

        return null;
    }
}