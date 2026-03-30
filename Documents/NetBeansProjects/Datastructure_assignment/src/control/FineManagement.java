/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author Yang
 */
import adt.DoublyLinkedList;
import java.time.LocalDate;
import adt.ListInterface;
import entity.*;
import utility.DateUtil;
import utility.FineCalculator;
import java.time.LocalDate;

public class FineManagement {
    
    private ListInterface<Fine> fineList = new DoublyLinkedList<>();
    
    public String calculateFine(Student student, Book book, LocalDate dueDate) {
    int overdueDays = DateUtil.calculateOverdueDays(dueDate);

    if (overdueDays == 0) {
        return "No overdue. No fine.";
    }

    double amount = FineCalculator.calculateFine(overdueDays);

    Fine fine = new Fine(
        generateFineID(),
        student,
        book,
        overdueDays,
        amount,
        "Unpaid"
    );

    fineList.add(fine);

    return "Fine created: RM " + amount;
    }
    
    public String displayUnpaidFines() {
    String output = "";

    for (int i = 1; i <= fineList.size(); i++) {
        Fine f = fineList.get(i);

        if (f.getStatus().equalsIgnoreCase("Unpaid")) {
            output += f.getFineID() + " | "
                   + f.getStudent().getStudentID() + " | "
                   + f.getBook().getTitle() + " | RM "
                   + f.getAmount() + "\n";
        }
    }

    return output.isEmpty() ? "No unpaid fines." : output;
    }
    
    public String payFine(String fineID) {
    for (int i = 1; i <= fineList.size(); i++) {
        Fine f = fineList.get(i);

        if (f.getFineID().equalsIgnoreCase(fineID)) {
            if (f.getStatus().equals("Paid")) {
                return "Fine already paid.";
            }

            f.setStatus("Paid");
            return "Fine paid successfully.";
        }
    }

    return "Fine not found.";
    }
    
    public String viewStudentFines(String studentID) {
    String output = "";

    for (int i = 1; i <= fineList.size(); i++) {
        Fine f = fineList.get(i);

        if (f.getStudent().getStudentID().equalsIgnoreCase(studentID)) {
            output += f.getFineID() + " | "
                   + f.getBook().getTitle() + " | RM "
                   + f.getAmount() + " | "
                   + f.getStatus() + "\n";
        }
    }

    return output.isEmpty() ? "No fines found." : output;
    }
    
    private int fineCounter = 1;

    private String generateFineID() {
    return "F" + String.format("%03d", fineCounter++);
    }
}
