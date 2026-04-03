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
import dao.FineDAO;
import dao.BorrowRecordDAO;
import entity.Book;
import entity.Fine;
import entity.BorrowRecord;
import entity.Reservation;

public class ReportManagement {

    private ListInterface<BorrowRecord> borrowRecordList;
    private ListInterface<Book> bookList;
    private ListInterface<Reservation> reservationList;

    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private BookDAO bookDAO = new BookDAO();
    private ListInterface<Fine> fineList;
    private FineDAO fineDAO = new FineDAO();
    

    public ReportManagement() {
        borrowRecordList = borrowRecordDAO.retrieveFromFile();
        bookList = bookDAO.retrieveFromFile();
        fineList = fineDAO.retrieveFromFile();
        reservationList = new DoublyLinkedList<>();
    }

    public void setReservationList(ListInterface<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ListInterface<Reservation> getReservationList() {
        return reservationList;
    }

    public String getTop5MostBorrowedBooksReport() {
        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No borrowing records found.";
        }

        ListInterface<String> bookIDList = new DoublyLinkedList<>();
        ListInterface<Integer> borrowCountList = new DoublyLinkedList<>();
        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord record = borrowRecordList.get(i);

            if (record == null || record.getBookID() == null) {
                continue;
            }

            String currentBookID = record.getBookID().trim();

            int index = bookIDList.indexOf(currentBookID);

            if (index == -1) {
                bookIDList.add(currentBookID);
                borrowCountList.add(1);
            } else {
                int currentCount = borrowCountList.get(index);
                borrowCountList.set(index, currentCount + 1);
            }
        }

        if (bookIDList.isEmpty()) {
            return "No borrowing records found.";
        }

        for (int i = 1; i <= borrowCountList.size() - 1; i++) {
            for (int j = 1; j <= borrowCountList.size() - i; j++) {
                if (borrowCountList.get(j) < borrowCountList.get(j + 1)) {
                    
                    int tempCount = borrowCountList.get(j);
                    borrowCountList.set(j, borrowCountList.get(j + 1));
                    borrowCountList.set(j + 1, tempCount);

                    String tempBookID = bookIDList.get(j);
                    bookIDList.set(j, bookIDList.get(j + 1));
                    bookIDList.set(j + 1, tempBookID);
                }
            }
        }

        output.append(String.format("%-5s %-10s %-35s %-10s%n", 
                "No", "Book ID", "Book Title", "Borrowed"));
        output.append("---------------------------------------------------------------------\n");

        int limit = Math.min(5, bookIDList.size());

        for (int i = 1; i <= limit; i++) {
            String bookID = bookIDList.get(i);
            int count = borrowCountList.get(i);
            Book book = findBookById(bookID);

            output.append(String.format("%-5d %-10s %-35s %-10d%n",
                    i,
                    bookID,
                    book != null ? book.getTitle() : "Unknown Title",
                    count));
        }

        return output.toString();
    }
   
    public String getTop5MostActiveBorrowersReport() {

        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return "No borrowing records found.";
        }

        ListInterface<String> borrowerIDList = new DoublyLinkedList<>();
        ListInterface<Integer> borrowCountList = new DoublyLinkedList<>();
        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord record = borrowRecordList.get(i);

            if (record == null || record.getBorrowerID() == null) {
                continue;
            }

            String borrowerID = record.getBorrowerID().trim();
            int index = borrowerIDList.indexOf(borrowerID);

            if (index == -1) {
                borrowerIDList.add(borrowerID);
                borrowCountList.add(1);
            } else {
                int currentCount = borrowCountList.get(index);
                borrowCountList.set(index, currentCount + 1);
            }
        }

        if (borrowerIDList.isEmpty()) {
            return "No borrowing records found.";
        }

        for (int i = 1; i <= borrowCountList.size() - 1; i++) {
            for (int j = 1; j <= borrowCountList.size() - i; j++) {
                if (borrowCountList.get(j) < borrowCountList.get(j + 1)) {

                    int tempCount = borrowCountList.get(j);
                    borrowCountList.set(j, borrowCountList.get(j + 1));
                    borrowCountList.set(j + 1, tempCount);

                    String tempID = borrowerIDList.get(j);
                    borrowerIDList.set(j, borrowerIDList.get(j + 1));
                    borrowerIDList.set(j + 1, tempID);
                }
            }
        }
        
        output.append(String.format("%-5s %-12s %-25s %-10s%n",
                "No", "Student ID", "Student Name", "Total Borrow"));
        output.append("------------------------------------------------------------------\n");

        int limit = Math.min(5, borrowerIDList.size());

        for (int i = 1; i <= limit; i++) {
            String borrowerID = borrowerIDList.get(i);
            int count = borrowCountList.get(i);

            String studentName = findStudentNameByIdFromRecords(borrowerID);

            output.append(String.format("%-5d %-12s %-25s %-10d%n",
                    i,
                    borrowerID,
                    studentName != null ? studentName : "Unknown",
                    count));
        }

        return output.toString();
    }
   
    public String getTop5MostUnpaidFineBorrowersReport() {
        if (fineList == null || fineList.isEmpty()) {
            return "No fine records found.";
        }

        ListInterface<String> borrowerIDList = new DoublyLinkedList<>();
        ListInterface<Integer> unpaidCountList = new DoublyLinkedList<>();
        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= fineList.size(); i++) {
            Fine fine = fineList.get(i);

            if (fine == null
                    || fine.getBorrowRecord() == null
                    || fine.getBorrowRecord().getBorrowerID() == null
                    || fine.getStatus() == null) {
                continue;
            }

            if (!fine.getStatus().equalsIgnoreCase("Unpaid")) {
                continue;
            }

            String borrowerID = fine.getBorrowRecord().getBorrowerID().trim();
            int index = borrowerIDList.indexOf(borrowerID);

            if (index == -1) {
                borrowerIDList.add(borrowerID);
                unpaidCountList.add(1);
            } else {
                int currentCount = unpaidCountList.get(index);
                unpaidCountList.set(index, currentCount + 1);
            }
        }

        if (borrowerIDList.isEmpty()) {
            return "No unpaid fine records found.";
        }

        for (int i = 1; i <= unpaidCountList.size() - 1; i++) {
            for (int j = 1; j <= unpaidCountList.size() - i; j++) {
                if (unpaidCountList.get(j) < unpaidCountList.get(j + 1)) {

                    int tempCount = unpaidCountList.get(j);
                    unpaidCountList.set(j, unpaidCountList.get(j + 1));
                    unpaidCountList.set(j + 1, tempCount);

                    String tempID = borrowerIDList.get(j);
                    borrowerIDList.set(j, borrowerIDList.get(j + 1));
                    borrowerIDList.set(j + 1, tempID);
                }
            }
        }

        output.append(String.format("%-5s %-12s %-25s %-15s%n",
                "No", "Student ID", "Student Name", "Unpaid Fine Count"));
        output.append("--------------------------------------------------------------------------\n");

        int limit = Math.min(5, borrowerIDList.size());

        for (int i = 1; i <= limit; i++) {
            String borrowerID = borrowerIDList.get(i);
            int count = unpaidCountList.get(i);
            String studentName = findStudentNameFromFineRecords(borrowerID);

            output.append(String.format("%-5d %-12s %-25s %-15d%n",
                    i,
                    borrowerID,
                    studentName,
                    count));
        }

        return output.toString();
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
    
    private String findStudentNameByIdFromRecords(String studentId) {
        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord record = borrowRecordList.get(i);

            if (record != null
                    && record.getBorrowerID() != null
                    && record.getBorrowerID().equalsIgnoreCase(studentId.trim())
                    && record.getBorrowName() != null
                    && !record.getBorrowName().trim().isEmpty()) {
                return record.getBorrowName();
            }
        }
        return "Unknown";
    }
    
    private String findStudentNameFromFineRecords(String studentId) {
        for (int i = 1; i <= fineList.size(); i++) {
            Fine fine = fineList.get(i);

            if (fine != null
                    && fine.getBorrowRecord() != null
                    && fine.getBorrowRecord().getBorrowerID() != null
                    && fine.getBorrowRecord().getBorrowerID().equalsIgnoreCase(studentId.trim())
                    && fine.getBorrowRecord().getBorrowName() != null
                    && !fine.getBorrowRecord().getBorrowName().trim().isEmpty()) {
                return fine.getBorrowRecord().getBorrowName();
            }
        }
        return "Unknown";
    }
}