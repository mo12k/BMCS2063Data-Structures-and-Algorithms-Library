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
import utility.MessageUI;


/**
 *
 * @author user
 */
public class BorrowReturnBook {

    private static final int TITLE_COL_WIDTH = 45;
    private static final int AUTHOR_COL_WIDTH = 20;
    private static final int CATEGORY_COL_WIDTH = 20;
    private static final int RECORD_ID_COL_WIDTH = 8;
    private static final int STUDENT_ID_COL_WIDTH = 10;
    private static final int STUDENT_NAME_COL_WIDTH = 18;
    private static final int BOOK_ID_COL_WIDTH = 8;
    private static final int BOOK_NAME_COL_WIDTH = 28;
    private static final int DATE_COL_WIDTH = 12;
    private static final int STATUS_COL_WIDTH = 10;

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
                          .append("\n");
                }
            }

            return output.toString();
        }
        
         public String searchBook(String keyword) {
            reloadData();

            ListInterface<Book> matchedBooks = new DoublyLinkedList<>();

            if (keyword == null) {
                keyword = "";
            }

            String searchKey = keyword.trim().toLowerCase();

            for (int i = 1; i <= bookList.size(); i++) {
                Book book = bookList.get(i);

                if (book == null) {
                    continue;
                }

                String id = safe(book.getBookID()).toLowerCase();
                String title = safe(book.getTitle()).toLowerCase();
                String author = safe(book.getAuthor()).toLowerCase();
                String category = safe(book.getCategory()).toLowerCase();

                if (searchKey.isEmpty()
                        || id.contains(searchKey)
                        || title.contains(searchKey)
                        || author.contains(searchKey)
                        || category.contains(searchKey)) {
                    matchedBooks.add(book);
                }
            }

            return formatBooksForDisplay(matchedBooks);
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
            MessageUI.displayMessage("Reservation module is not available.");
            return false;
        }
<<<<<<< Updated upstream
        else{
        reservationControl.reserveBook(studentId , bookId, studentName);
        }
   return true;
=======

        String result = reservationControl.reserveBook(studentId, bookId, studentName);
        MessageUI.displayMessage(result);

        return result.toLowerCase().contains("successful");
>>>>>>> Stashed changes
    }
    
    
    private void reloadData() {
    bookList = bookDAO.retrieveFromFile();
    borrowRecordList = borrowRecordDAO.retrieveFromFile();
}
    

    // Author: lamzh
    public void setReservationControl(BookReservation reservationControl) {
        this.reservationControl = reservationControl;
    }

    public boolean isReservationModuleAvailable() {
        return reservationControl != null;
    }

    public String searchBookForWaitingList(String keyword) {
        reloadData();
        return searchBook(keyword);
    }

    public String viewWaitingList(String bookId) {
        if (reservationControl == null) {
            return "Reservation module is not available.";
        }
        return reservationControl.viewWaitingList(bookId);
    }
    
    private String formatBorrowRecordsForDisplay(ListInterface<BorrowRecord> records) {
            if (records == null || records.isEmpty()) {
                return "No borrow records found.";
            }

            String header = String.format(
                    "%-" + RECORD_ID_COL_WIDTH + "s | %-" + STUDENT_ID_COL_WIDTH + "s | %-" + STUDENT_NAME_COL_WIDTH + "s | %-" + BOOK_ID_COL_WIDTH + "s | %-" + BOOK_NAME_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + STATUS_COL_WIDTH + "s",
                    "RecordID", "StudentID", "Student Name", "BookID", "Book Name",
                    "Borrow Date", "Return Date", "Expiry Date", "Status"
            );

            String line = "-".repeat(header.length());
            StringBuilder output = new StringBuilder();

            output.append(header).append("\n");
            output.append(line).append("\n");

            for (int i = 1; i <= records.size(); i++) {
                BorrowRecord record = records.get(i);
                if (record == null) {
                    continue;
                }

                Book book = findBookById(record.getBookID());
                String bookName = (book == null) ? "Unknown" : book.getTitle();

                output.append(String.format(
                        "%-" + RECORD_ID_COL_WIDTH + "s | %-" + STUDENT_ID_COL_WIDTH + "s | %-" + STUDENT_NAME_COL_WIDTH + "s | %-" + BOOK_ID_COL_WIDTH + "s | %-" + BOOK_NAME_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + DATE_COL_WIDTH + "s | %-" + STATUS_COL_WIDTH + "s",
                        safe(record.getRecordID()),
                        safe(record.getBorrowerID()),
                        safe(record.getBorrowName()),
                        safe(record.getBookID()),
                        safe(bookName),
                        safe(record.getBorrowDate()),
                        safe(record.getReturnDate() == null ? "-" : record.getReturnDate()),
                        safe(record.getExpiryDate()),
                        safe(record.getStatus())
                ));
                output.append("\n");
            }

            return output.toString();
        }
    
    private String safe(String text) {
            return text == null ? "" : text;
        }
    
    private String formatBooksForDisplay(ListInterface<Book> books) {
    if (books == null || books.isEmpty()) {
        return "No books found.";
    }

    String header = String.format(
            "%-6s | %-" + TITLE_COL_WIDTH + "s | %-" + AUTHOR_COL_WIDTH + "s | %-" + CATEGORY_COL_WIDTH + "s | %-4s | %-3s | %-9s | %-7s",
            "BookID", "Title", "Author", "Category", "Year", "Qty", "Available", "Waiting"
    );

    String line = "-".repeat(header.length());
    StringBuilder output = new StringBuilder();

    output.append(header).append("\n");
    output.append(line).append("\n");

    for (int i = 1; i <= books.size(); i++) {
        Book book = books.get(i);
        if (book == null) {
            continue;
        }

        output.append(String.format(
                "%-6s | %-" + TITLE_COL_WIDTH + "s | %-" + AUTHOR_COL_WIDTH + "s | %-" + CATEGORY_COL_WIDTH + "s | %-4d | %-3d | %-9s | %-7d",
                safe(book.getBookID()),
                safe(book.getTitle()),
                safe(book.getAuthor()),
                safe(book.getCategory()),
                book.getYearPublished(),
                book.getQuantity(),
                book.isIsAvailable() ? "Yes" : "No",
                book.getWaitingListCount()
        ));
        output.append("\n");
    }

    return output.toString();
}


}
