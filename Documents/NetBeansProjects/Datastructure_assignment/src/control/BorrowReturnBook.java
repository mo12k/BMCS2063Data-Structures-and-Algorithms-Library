///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package control;
//
//
//import adt.*;
//import dao.*;
//import entity.*;
//
//
///**
// *
// * @author user
// */
//public class BorrowReturnBook {
//    private ListInterface<Book> bookList = new ArrayList<>();
//    private ListInterface<Borrower> borrowerList = new ArrayList<>();
//    private ListInterface<BorrowRecord> borrowRecordList = new ArrayList<>();
//    private BookDAO bookDAO = new BookDAO();
//    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
//    
//    
//    public Book findBookById(String bookId) {
//    if (bookId == null || bookId.trim().isEmpty()) {
//        return null;
//    }
//
//    for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
//        Book book = bookList.getEntry(i);
//        if (book != null && book.getBookID() != null
//                && book.getBookID().equalsIgnoreCase(bookId.trim())) {
//            return book;
//        }
//    }
//    return null;
//}
//    
//        public Borrower findBorrowerById(String borrowerId) {
//        if (borrowerId == null || borrowerId.trim().isEmpty()) {
//            return null;
//        }
//
//        for (int i = 1; i <= borrowerList.getNumberOfEntries(); i++) {
//            Borrower borrower = borrowerList.getEntry(i);
//            if (borrower != null && borrower.getBorrowerID() != null
//                    && borrower.getBorrowerID().equalsIgnoreCase(borrowerId.trim())) {
//                return borrower;
//            }
//        }
//        return null;
//    }
//
//        public boolean returnBook(String borrowerId, String bookId) {
//        BorrowRecord record = findActiveBorrowRecord(borrowerId, bookId);
//        Book book = findBookById(bookId);
//
//        if (record == null || book == null) {
//            return false;
//        }
//
//        record.setReturnDate();
//        record.setStatus("RETURNED");
//
//        book.setQuantity(book.getQuantity() + 1);
//        book.setIsAvailable(true);
//
//        bookDAO.saveToFile(bookList);
//        borrowRecordDAO.saveToFile(borrowRecordList);
//
//        return true;
//    }
//
//        public BorrowRecord findActiveBorrowRecord(String borrowerId, String bookId) {
//        if (borrowerId == null || bookId == null) {
//            return null;
//        }
//
//        for (int i = 1; i <= borrowRecordList.getNumberOfEntries(); i++) {
//            BorrowRecord record = borrowRecordList.getEntry(i);
//
//            if (record != null
//                    && record.getBorrowerID() != null
//                    && record.getBookID() != null
//                    && record.getStatus() != null
//                    && record.getBorrowerID().equalsIgnoreCase(borrowerId.trim())
//                    && record.getBookID().equalsIgnoreCase(bookId.trim())
//                    && record.getStatus().equalsIgnoreCase("BORROWED")) {
//                return record;
//            }
//        }
//        return null;
//    }
//
//        public boolean checkAvailability(String bookId) {
//        Book book = findBookById(bookId);
//        return book != null && book.getQuantity() > 0 && book.isIsAvailable();
//    }
//
//        public boolean borrowBook(String borrowerId, String bookId) {
//        Borrower borrower = findBorrowerById(borrowerId);
//        Book book = findBookById(bookId);
//
//        if (borrower == null || book == null) {
//            return false;
//        }
//
//        if (book.getQuantity() <= 0 || !book.isIsAvailable()) {
//            return false;
//        }
//
//        
//        if (findActiveBorrowRecord(borrowerId, bookId) != null) {
//            return false;
//        }
//
//        book.setQuantity(book.getQuantity() - 1);
//        book.setIsAvailable(book.getQuantity() > 0);
//
//        BorrowRecord newRecord = new BorrowRecord(
//                borrower.getBorrowerID(),
//                book.getBookID()
//        );
//
//        borrowRecordList.add(newRecord);
//
//        bookDAO.saveToFile(bookList);
//        borrowRecordDAO.saveToFile(borrowRecordList);
//
//        return true;
//    }
//
//      
//
//        public ListInterface<BorrowRecord> getBorrowedBooks() {
//        ListInterface<BorrowRecord> borrowedList = new ArrayList<>();
//
//        for (int i = 1; i <= borrowRecordList.getNumberOfEntries(); i++) {
//            BorrowRecord record = borrowRecordList.getEntry(i);
//
//            if (record != null && record.getStatus() != null
//                    && record.getStatus().equalsIgnoreCase("BORROWED")) {
//                borrowedList.add(record);
//            }
//        }
//
//        return borrowedList;
//    }
//        public String getAllBorrowedBooks() {
//        ListInterface<BorrowRecord> borrowedList = getBorrowedBooks();
//        String outputStr = "";
//
//        for (int i = 1; i <= borrowedList.getNumberOfEntries(); i++) {
//            outputStr += borrowedList.getEntry(i) + "\n";
//        }
//
//        return outputStr;
//    }
//        
//        public String searchBook(String keyword) {
//    String outputStr = "";
//
//    if (keyword == null || keyword.trim().isEmpty()) {
//        return outputStr;
//    }
//
//    for (int i = 1; i <= bookList.getNumberOfEntries(); i++) {
//        Book book = bookList.getEntry(i);
//
//        if (book != null) {
//            String title = book.getTitle() == null ? "" : book.getTitle();
//            String author = book.getAuthor() == null ? "" : book.getAuthor();
//            String category = book.getCategory() == null ? "" : book.getCategory();
//
//            if (title.toLowerCase().contains(keyword.toLowerCase())
//                    || author.toLowerCase().contains(keyword.toLowerCase())
//                    || category.toLowerCase().contains(keyword.toLowerCase())) {
//                outputStr += book.toString() + "\n";
//            }
//        }
//    }
//
//    return outputStr;
//}
//    
//}
