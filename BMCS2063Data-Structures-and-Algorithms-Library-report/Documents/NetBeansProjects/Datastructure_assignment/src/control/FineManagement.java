/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author Yang
 */

import java.time.LocalDate;
import adt.ListInterface;
import dao.FineDAO;
import entity.BorrowRecord;
import entity.Fine;
import utility.DateUtil;
import utility.FineCalculator;

public class FineManagement {

    private ListInterface<Fine> fineList;
    private FineDAO fineDAO = new FineDAO();
    private int fineCounter = 1;

    public FineManagement() {
        fineList = fineDAO.retrieveFromFile();
    }

    public String calculateFine(BorrowRecord record) {
        if (record == null) {
            return "Fine creation failed. Borrow record is null.";
        }

        if (record.getExpiryDate() == null || record.getExpiryDate().isEmpty()) {
            return "Fine creation failed. Expiry date is missing.";
        }

        // optional: only fine overdue borrowed records
        if (!record.getStatus().equalsIgnoreCase("BORROWED")
                && !record.getStatus().equalsIgnoreCase("EXPIRED")) {
            return "No fine applicable for this record status.";
        }

        Fine existingFine = findUnpaidFine(record.getBorrowerID(), record.getBookID());
        if (existingFine != null) {
            return "Unpaid fine already exists for this student and book.";
        }

        LocalDate dueDate = LocalDate.parse(record.getExpiryDate());
        int overdueDays = DateUtil.calculateOverdueDays(dueDate);

        if (overdueDays == 0) {
            return "No overdue. No fine.";
        }

        double amount = FineCalculator.calculateFine(overdueDays);

        Fine fine = new Fine(
                generateFineID(),
                record,
                overdueDays,
                amount,
                "Unpaid"
        );

        fineList.add(fine);
        fineDAO.saveToFile(fineList);

        return "Fine created: RM " + amount;
    }

    public String displayUnpaidFines() {
        String output = "";

        for (int i = 1; i <= fineList.size(); i++) {
            Fine f = fineList.get(i);

            if (f != null
                    && f.getBorrowRecord() != null
                    && f.getStatus().equalsIgnoreCase("Unpaid")) {

                output += f.getFineID() + " | "
                        + f.getBorrowRecord().getBorrowerID() + " | "
                        + f.getBorrowRecord().getBookID() + " | RM "
                        + f.getAmount() + " | "
                        + f.getOverdueDays() + " day(s)\n";
            }
        }

        return output.isEmpty() ? "No unpaid fines." : output;
    }

    public String payFine(String fineID) {
        for (int i = 1; i <= fineList.size(); i++) {
            Fine f = fineList.get(i);

            if (f != null && f.getFineID().equalsIgnoreCase(fineID)) {
                if (f.getStatus().equalsIgnoreCase("Paid")) {
                    return "Fine already paid.";
                }

                f.setStatus("Paid");
                fineDAO.saveToFile(fineList);
                return "Fine paid successfully.";
            }
        }

        return "Fine not found.";
    }

    public String viewStudentFines(String studentID) {
        String output = "";

        for (int i = 1; i <= fineList.size(); i++) {
            Fine f = fineList.get(i);

            if (f != null
                    && f.getBorrowRecord() != null
                    && f.getBorrowRecord().getBorrowerID().equalsIgnoreCase(studentID)) {

                output += f.getFineID() + " | "
                        + f.getBorrowRecord().getBookID() + " | RM "
                        + f.getAmount() + " | "
                        + f.getStatus() + "\n";
            }
        }

        return output.isEmpty() ? "No fines found." : output;
    }

    private Fine findUnpaidFine(String studentID, String bookID) {
        for (int i = 1; i <= fineList.size(); i++) {
            Fine fine = fineList.get(i);

            if (fine != null
                    && fine.getBorrowRecord() != null
                    && fine.getStatus().equalsIgnoreCase("Unpaid")
                    && fine.getBorrowRecord().getBorrowerID().equalsIgnoreCase(studentID)
                    && fine.getBorrowRecord().getBookID().equalsIgnoreCase(bookID)) {
                return fine;
            }
        }
        return null;
    }

    private String generateFineID() {
        return "F" + String.format("%03d", fineCounter++);
    }
}