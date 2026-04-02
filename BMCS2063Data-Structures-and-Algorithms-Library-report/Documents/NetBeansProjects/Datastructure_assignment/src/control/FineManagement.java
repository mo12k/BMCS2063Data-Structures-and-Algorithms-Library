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
        initializeFineCounter();
    }

    public void autoGenerateFines(ListInterface<BorrowRecord> recordList) {
        for (int i = 1; i <= recordList.size(); i++) {
            BorrowRecord record = recordList.get(i);

            if (record == null || record.getExpiryDate() == null || record.getStatus() == null) {
                continue;
            }

            LocalDate dueDate = LocalDate.parse(record.getExpiryDate());
            int overdueDays = DateUtil.calculateOverdueDays(dueDate);

            if (overdueDays > 0
                    && !record.getStatus().equalsIgnoreCase("RETURNED")) {

                Fine existingFine = findUnpaidFine(record.getBorrowerID(), record.getBookID());

                if (existingFine == null) {
                    Fine fine = new Fine(
                            generateFineID(),
                            record,
                            overdueDays,
                            FineCalculator.calculateFine(overdueDays),
                            "Unpaid"
                    );

                    fineList.add(fine);
                }
            }
        }

        fineDAO.saveToFile(fineList);
    }

    public String displayUnpaidFines() {
        String output = "";

        for (int i = 1; i <= fineList.size(); i++) {
            Fine f = fineList.get(i);

            if (f != null
                    && f.getBorrowRecord() != null
                    && f.getStatus().equalsIgnoreCase("Unpaid")) {

                output += String.format("%-7s | %-10s | %-7s | %-8s | %-10s%n",
                        f.getFineID(),
                        f.getBorrowRecord().getBorrowerID(),
                        f.getBorrowRecord().getBookID(),
                        "RM " + String.format("%.2f", f.getAmount()),
                        f.getOverdueDays() + " day(s)");
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

                output += String.format("%-7s | %-7s | %-8s | %-6s%n",
                        f.getFineID(),
                        f.getBorrowRecord().getBorrowerID(),
                        "RM " + String.format("%.2f", f.getAmount()),
                        f.getStatus());
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

        private void initializeFineCounter() {
        int max = 0;

        for (int i = 1; i <= fineList.size(); i++) {
            Fine f = fineList.get(i);

            if (f != null && f.getFineID() != null) {
                try {
                    // F001 → 001 → 1
                    int num = Integer.parseInt(f.getFineID().substring(1));
                    if (num > max) {
                        max = num;
                    }
                } catch (Exception e) {
                    
                }
            }
        }

        fineCounter = max + 1;
    }
}