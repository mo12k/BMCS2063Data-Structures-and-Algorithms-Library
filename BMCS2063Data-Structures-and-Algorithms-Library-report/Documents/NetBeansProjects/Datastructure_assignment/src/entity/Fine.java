/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Yang
 */

import java.io.Serializable;

public class Fine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fineID;
    private BorrowRecord borrowRecord;
    private int overdueDays;
    private double amount;
    private String status; // Unpaid / Paid

    public Fine(String fineID, BorrowRecord borrowRecord, int overdueDays, double amount, String status) {
        this.fineID = fineID;
        this.borrowRecord = borrowRecord;
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

    public BorrowRecord getBorrowRecord() {
        return borrowRecord;
    }

    public void setBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecord = borrowRecord;
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
    
    @Override
    public String toString() {
        return String.format("%-7s | %-10s | RM %-8.2f | %-10s | %d day(s)",
                fineID,
                borrowRecord.getBookID(),
                amount,
                status,
                overdueDays);
    }
    
}
