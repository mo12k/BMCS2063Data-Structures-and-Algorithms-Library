/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import adt.*;
import boundary.BookReservationUI;
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
            
         
           if(borrowRecordList.isEmpty()){
                   initializeSampleRecord();
           }}
    
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
    

        public String findStudentNameById(String studentId) {
             reloadData();
                for (int i = 1; i <= borrowRecordList.size(); i++) {
                    BorrowRecord record = borrowRecordList.get(i);
                    if (record != null
                            && record.getBorrowerID() != null
                            && record.getBorrowerID().equalsIgnoreCase(studentId.trim())
                            && record.getBorrowName()!= null
                            && !record.getBorrowName().trim().isEmpty()) {
                        return record.getBorrowName();
                    }
                }
                return null;
            }
    
        public boolean returnBook(String studentId, String bookId) {
            reloadData();
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


       public int borrowBook(String studentId, String bookId, String studentName) {

                reloadData(); 

                if (!isValidStudentId(studentId)) {
                    return -1; 
                }

                Book book = findBookById(bookId);

                if (book == null) {
                    return -2; 
                }

        
                if (findActiveBorrowRecord(studentId, bookId) != null) {
                    return -3; 
                }

                if (!checkAvailability(bookId)) {
                    return -4; 
                }

                book.setQuantity(book.getQuantity() - 1);
                book.setIsAvailable(book.getQuantity() > 0);

                BorrowRecord newRecord = new BorrowRecord(studentId, bookId, studentName);
                borrowRecordList.add(newRecord);

                bookDAO.saveToFile(bookList);
                borrowRecordDAO.saveToFile(borrowRecordList);

                return 1; 

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
        
        
        public String getRecordsByStatus(String status) {
            reloadData();
            StringBuilder output = new StringBuilder();

            for (int i = 1; i <= borrowRecordList.size(); i++) {
                BorrowRecord record = borrowRecordList.get(i);

                if (record != null
                        && record.getStatus() != null
                        && record.getStatus().equalsIgnoreCase(status)) {

                  
                    Book book = findBookById(record.getBookID());
                    String bookName = (book == null) ? "Unknown" : book.getTitle();

                    output.append("Book ID: ").append(record.getBookID())
                          .append(" | Book Name: ").append(bookName)
                          .append(" | Student: ").append(record.getBorrowerID())
                          .append(" | Student Name: ").append(record.getBorrowName())
                          .append(" | Borrow Date: ").append(record.getBorrowDate())
                          .append(" | Return Date: ").append(record.getReturnDate() == null ? "-" : record.getReturnDate())
                          .append(" | Status: ").append(record.getStatus())
                          .append("\n");
                }
            }

            return output.toString();
        }
        
    public String searchBook(String keyword) {
        reloadData();
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
    reloadData();
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
    //Yang
    public BorrowRecord findBorrowRecordForFine(String studentId, String bookId) {
    for (int i = 1; i <= borrowRecordList.size(); i++) {
        BorrowRecord record = borrowRecordList.get(i);

        if (record != null
                && record.getBorrowerID() != null
                && record.getBookID() != null
                && record.getStatus() != null
                && record.getBorrowerID().equalsIgnoreCase(studentId.trim())
                && record.getBookID().equalsIgnoreCase(bookId.trim())
                && (record.getStatus().equalsIgnoreCase("BORROWED")
                    || record.getStatus().equalsIgnoreCase("EXPIRED"))) {
            return record;
        }
    }
    return null;
}



    public void initializeSampleRecord() {
    LocalDate today = LocalDate.now();


                    borrowRecordList.add(new BorrowRecord(
                            "ST001", "Alice Tan", "B0001",
                            today.minusDays(6).toString(),
                            null,
                            today.minusDays(6).plusDays(31).toString(),
                            "BORROWED"
                    ));

                    borrowRecordList.add(new BorrowRecord(
                            "ST002", "Ben Lim", "B0002",
                            today.minusDays(7).toString(),
                            null,
                            today.minusDays(7).plusDays(31).toString(),
                            "BORROWED"
                    ));

                    borrowRecordList.add(new BorrowRecord(
                            "ST003", "Chloe Wong", "B0003",
                            today.minusDays(8).toString(),
                            null,
                            today.minusDays(8).plusDays(31).toString(),
                            "BORROWED"
                    ));

                    // EXPIRED
                    borrowRecordList.add(new BorrowRecord(
                            "ST004", "Daniel Lee", "B0004",
                            today.minusDays(45).toString(),
                            null,
                            today.minusDays(45).plusDays(31).toString(),
                            "EXPIRED"
                    ));

                    borrowRecordList.add(new BorrowRecord(
                            "ST005", "Emily Ng", "B0005",
                            today.minusDays(50).toString(),
                            null,
                            today.minusDays(50).plusDays(31).toString(),
                            "EXPIRED"
                    ));

                    borrowRecordList.add(new BorrowRecord(
                            "ST006", "Farah Ahmad", "B0006",
                            today.minusDays(55).toString(),
                            null,
                            today.minusDays(55).plusDays(31).toString(),
                            "EXPIRED"
                    ));

                    // RETURNED
                    borrowRecordList.add(new BorrowRecord(
                            "ST007", "Gary Chong", "B0007",
                            today.minusDays(25).toString(),
                            today.minusDays(10).toString(),
                            today.minusDays(25).plusDays(31).toString(),
                            "RETURNED"
                    ));

                    borrowRecordList.add(new BorrowRecord(
                            "ST008", "Hannah Ong", "B0008",
                            today.minusDays(30).toString(),
                            today.minusDays(12).toString(),
                            today.minusDays(30).plusDays(31).toString(),
                            "RETURNED"
                    ));
                borrowRecordDAO.saveToFile(borrowRecordList);
            }
    
    public String getActiveBorrowRecordStringByStudent(String studentId) {
    StringBuilder output = new StringBuilder();

    for (int i = 1; i <= borrowRecordList.size(); i++) {
        BorrowRecord record = borrowRecordList.get(i);

        if (record != null
                && record.getBorrowerID()!= null
                && record.getBorrowerID().equalsIgnoreCase(studentId.trim())
                && record.getStatus() != null
                && (record.getStatus().equalsIgnoreCase("BORROWED") || record.getStatus().equalsIgnoreCase("EXPIRED"))
                ) {

            Book book = findBookById(record.getBookID());
            String bookTitle = (book == null) ? "Unknown Book" : book.getTitle();

            output.append("Book ID: ").append(record.getBookID())
                  .append(" | Book Name: ").append(bookTitle)
                  .append(" | Borrow Date: ").append(record.getBorrowDate())
                  .append(" | Expiry Date: ").append(record.getExpiryDate())
                  .append(" | Status: ").append(record.getStatus())
                  .append("\n");
        }
    }

    return output.toString();
}
    
    
    public String formatBorrowRecord(BorrowRecord record) {
    Book book = findBookById(record.getBookID());

    String bookName =  book.getTitle();

    return String.format("%s | Student: %s | StudentName: %s (%s) | Book: %s (%s) | Borrow Date: %s | Return Date: %s | Expiry Date: %s | Status: %s",
            record.getRecordID(),
            record.getBorrowerID(),
            record.getBorrowName(),
            bookName,
            record.getBookID(),
            record.getBorrowDate(),
            record.getReturnDate() == null ? "-" : record.getReturnDate(),
            record.getExpiryDate(),
            record.getStatus());
}
    
    
    

    private BookReservation reservationControl;

    public BorrowReturnBook(BookReservation reservationControl) {
        this.reservationControl = reservationControl;
        bookList = bookDAO.retrieveFromFile();
        borrowRecordList = borrowRecordDAO.retrieveFromFile();
    }

    public boolean addToWaitingList(String studentId, String bookId,String studentName) {
        if (reservationControl == null) {
            System.out.println("Reservation module is not available.");
            return false;
        }
        else{
        reservationControl.reserveBook(studentId , bookId, studentName);
        }
   return true;
    }
    
    
    private void reloadData() {
    bookList = bookDAO.retrieveFromFile();
    borrowRecordList = borrowRecordDAO.retrieveFromFile();
}
    

}
