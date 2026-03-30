/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author Yang
 */

import control.ReportManagement;
import java.util.Scanner;

public class ReportUI {

    private Scanner scanner = new Scanner(System.in);
    private ReportManagement reportControl = new ReportManagement();

    public void startReportModule() {
        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("           REPORT MODULE");
            System.out.println("======================================");
            System.out.println("1. Current Reserve Report");
            System.out.println("2. Borrowed Books Report");
            System.out.println("3. Overdue Books Report");
            System.out.println("4. Most Borrowed Books Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println(reportControl.getCurrentReserveReport());
                    break;
                case 2:
                    System.out.println(reportControl.getBorrowedBooksReport());
                    break;
                case 3:
                    System.out.println(reportControl.getOverdueBooksReport());
                    break;
                case 4:
                    System.out.println(reportControl.getMostBorrowedBooksReport());
                    break;
                case 0:
                    System.out.println("Exiting Report Module...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}
