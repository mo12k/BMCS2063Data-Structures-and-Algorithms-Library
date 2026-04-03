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

    public void setReservationList(ListInterface<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ListInterface<Reservation> getReservationList() {
        return reservationList;
    }

    public String getMostBorrowedBooksReport() {
        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No borrowing records found.";
        }

        ListInterface<String> countedBookIDs = new DoublyLinkedList<>();
        int highestCount = 0;
        StringBuilder output = new StringBuilder();

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

                output.append(String.format("%-10s %-30s %-15d%n",
                        bookID,
                        book != null ? book.getTitle() : "Unknown Title",
                        count));
            }
        }

        return output.length() == 0 ? "No borrowing records found." : output.toString();
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