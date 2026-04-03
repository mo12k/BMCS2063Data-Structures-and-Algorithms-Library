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
    private String studentName;
    private String bookID;
    private String borrowDate;
    private String returnDate;
    private String status;
    private String expiryDate;

    private static int recordCount = 1;

    public BorrowRecord() {
    }
    public BorrowRecord(String borrowerID, String bookID,String studentName) {
        
        this.recordID = generateRecordID();
        this.studentID = borrowerID;
        this.bookID = bookID;
        this.studentName=studentName;
        this.borrowDate =setDate.toString();
        this.returnDate = null;
        this.expiryDate = setExDate.toString();
        this.status = "BORROWED";
    }
    
    public BorrowRecord(String borrowerID, String studentName ,String bookID, String borrowDate, String returnDate, String expiryDate, String status) {
        this.recordID = generateRecordID();
        this.studentID = borrowerID;
        this.bookID = bookID;
        this.studentName=studentName;
        this.borrowDate =borrowDate;
        this.returnDate = returnDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    
    private String generateRecordID() {
        return String.format("R%04d", recordCount++);
    }
    
    public static void setRecordCount(int nextCount) {
    recordCount = nextCount;
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
    
     public String getBorrowName() {
        return studentName;
    }

    public void setBorrowName(String studentName) {
        this.studentName = studentName;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BorrowRecord other = (BorrowRecord) obj;

        if (this.recordID != null && other.recordID != null) {
            return this.recordID.equalsIgnoreCase(other.recordID);
        }

        if (this.studentID == null || other.studentID == null) {
            return false;
        }

        if (this.bookID == null || other.bookID == null) {
            return false;
        }

        if (this.status == null || other.status == null) {
            return false;
        }

        return this.studentID.equalsIgnoreCase(other.studentID)
                && this.bookID.equalsIgnoreCase(other.bookID)
                && this.status.equalsIgnoreCase(other.status);
    }

    @Override
    public int hashCode() {
        if (recordID != null) {
            return recordID.toUpperCase().hashCode();
        }

        String student = studentID == null ? "" : studentID.toUpperCase();
        String book = bookID == null ? "" : bookID.toUpperCase();
        String stat = status == null ? "" : status.toUpperCase();

        return (student + "|" + book + "|" + stat).hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s | Student: %s | StudentName: %s | Book: %s | Borrow Date: %s | Return Date: %s | Expiry Date: %s | Status: %s  ",
                recordID,
                studentID,
                studentName,
                bookID,
                borrowDate,
                returnDate == null ? "-" : returnDate,
                expiryDate,
                status);
    }
    
    public static BorrowRecord createProbe(String studentId, String bookId, String status) {
    BorrowRecord record = new BorrowRecord ();
    record.studentID = studentId;
    record.bookID = bookId;
    record.status = status;
    return record;
}
    
    public static void setNextRecordNumber(int nextNumber) {
    if (nextNumber <= 0) {
        recordCount = 1;
    } else {
        recordCount = nextNumber;
    }
}
    
    
}