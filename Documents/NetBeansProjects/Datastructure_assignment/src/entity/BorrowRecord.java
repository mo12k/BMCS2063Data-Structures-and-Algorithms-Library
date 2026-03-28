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
    private String recordID;
    private String borrowerID;
    private String bookID;
    private String borrowDate;
    private String returnDate;
    private String status;

    private static int recordCount = 1;

 

    public BorrowRecord(String borrowerID, String bookID) {
        this.recordID = generateRecordID();
        this.borrowerID = borrowerID;
        this.bookID = bookID;
        this.borrowDate =setDate.toString();
        this.returnDate = null;
        this.status = "BORROWED";
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
        return borrowerID;
    }

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate() {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate=setDate.toString();
    }

    public void setReturnDate() {
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
        return String.format("%s | Borrower: %s | Book: %s | Borrow Date: %s | Return Date: %s | Status: %s",
                recordID,
                borrowerID,
                bookID,
                borrowDate,
                returnDate == null ? "-" : returnDate,
                status);
    }
}