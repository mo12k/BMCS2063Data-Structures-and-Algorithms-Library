/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.time.LocalDate;
import java.io.Serializable;

public class BorrowRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    LocalDate setDate = LocalDate.now();
    LocalDate setExDate =setDate.plusDays(31);
    private String recordID;
    private String studentID;
    private String bookID;
    private String borrowDate;
    private String returnDate;
    private String status;
    private String expiryDate;

    private static int recordCount = 1;

 

    public BorrowRecord(String borrowerID, String bookID) {
        
        this.recordID = generateRecordID();
        this.studentID = borrowerID;
        this.bookID = bookID;
        this.borrowDate =setDate.toString();
        this.returnDate = null;
        this.expiryDate = setExDate.toString();
        this.status = "BORROWED";
    }
    
    public BorrowRecord(String borrowerID, String bookID , String borrowDate, String returnDate, String expiryDate, String status) {
        this.recordID = generateRecordID();
        this.studentID = borrowerID;
        this.bookID = bookID;
        this.borrowDate =borrowDate;
        this.returnDate = returnDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

  

    
    
    private String generateRecordID() {
        return String.format("R%04d", recordCount++);
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getBorrowerID() {
        return studentID;
    }

    public void setBorrowerID(String borrowerID) {
        this.studentID = borrowerID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s | Student: %s | Book: %s | Borrow Date: %s | Return Date: %s | Expiry Date: %s | Status: %s  ",
                recordID,
                studentID,
                bookID,
                borrowDate,
                returnDate == null ? "-" : returnDate,
                expiryDate,
                status);
    }
}