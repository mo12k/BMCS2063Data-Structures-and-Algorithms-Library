/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Yang
 */
public class Fine {
    private String fineID;
    private Student student;
    private Book book;
    private int overdueDays;
    private double amount;
    private String status; // Unpaid / Paid

    public Fine(String fineID, Student student, Book book, int overdueDays, double amount, String status) {
        this.fineID = fineID;
        this.student = student;
        this.book = book;
        this.overdueDays = overdueDays;
        this.amount = amount;
        this.status = status;
    }

    public String getFineID() {
        return fineID;
    }

    public void setFineID(String fineID) {
        this.fineID = fineID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
