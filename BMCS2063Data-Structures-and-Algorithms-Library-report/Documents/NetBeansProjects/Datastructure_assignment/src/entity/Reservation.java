package entity;

import java.io.Serializable;
import java.util.Objects;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationID;
    private String studentID;
    private String studentName;
    private String bookID;
    private String bookTitle;
    private String reservationDate;
    private String status;

    public Reservation() {
    }

    public Reservation(String reservationID, String studentID, String studentName,
                       String bookID, String bookTitle, String reservationDate, String status) {
        this.reservationID = reservationID;
        this.studentID = studentID;
        this.studentName = studentName;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public String getReservationID() {
        return reservationID;
    }
    
    

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.reservationID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Reservation other = (Reservation) obj;
        return Objects.equals(this.reservationID, other.reservationID);
    }

    @Override
    public String toString() {
        return String.format("%s | Student: %s (%s) | Book: %s (%s) | Date: %s | Status: %s",
                reservationID,
                studentName,
                studentID,
                bookTitle,
                bookID,
                reservationDate,
                status);
    }
}