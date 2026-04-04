/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author lamzh
 */

import java.io.Serializable;
import java.util.Objects;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String reservationID;
    private Book book;
    private String studentID;
    private String studentName;
    private String reservationDate;
    private String status;
    
    public Reservation() {
    }
    
    public Reservation(String reservationID, Book book, String studentID, String studentName, String reservationDate, String status) {
        this.reservationID = reservationID;
        this.book = book;
        this.studentID = studentID;
        this.studentName = studentName;
        this.reservationDate = reservationDate;
        this.status = status;
    }
    
    public String getReservationID() {
        return reservationID;
    }
    
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
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
        return String.format("%s | Book: %s | Student ID: %s | Student Name: %s | Date: %s | Status: %s",
                reservationID,
                (book != null ? book.getBookID() : "N/A"),
                (studentID != null ? studentID : "N/A"),
                (studentName != null ? studentName : "N/A"),
                reservationDate,
                status);
    }
}