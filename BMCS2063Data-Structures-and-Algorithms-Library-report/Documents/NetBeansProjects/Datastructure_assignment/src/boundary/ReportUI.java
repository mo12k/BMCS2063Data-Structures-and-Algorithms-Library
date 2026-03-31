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
            showMainHeader();
            choice = readInt();

            switch (choice) {
                case 1:
                    showCurrentReserveReport();
                    break;
                case 2:
                    showBorrowedBooksReport();
                    break;
                case 3:
                    showOverdueBooksReport();
                    break;
                case 4:
                    showMostBorrowedBooksReport();
                    break;
                case 0:
                    System.out.println("\nExiting Report Module...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private void showMainHeader() {
        System.out.println("\n==================================================");
        System.out.println("                  LIBRARY REPORT MODULE           ");
        System.out.println("==================================================");
        System.out.println("1. Current Reserve Report");
        System.out.println("2. Borrowed Books Report");
        System.out.println("3. Overdue Books Report");
        System.out.println("4. Most Borrowed Books Report");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
    }

    private void showCurrentReserveReport() {
        System.out.println("\n==================================================");
        System.out.println("               CURRENT RESERVE REPORT             ");
        System.out.println("==================================================");
        System.out.println("Reservation records that are currently active");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-12s %-12s %-10s %-15s %-12s%n",
                "Reserve ID", "Student ID", "Book ID", "Reserve Date", "Status");
        System.out.println("--------------------------------------------------");

        String result = reportControl.getCurrentReserveReport();
        System.out.println(result);

        pressEnterToContinue();
    }

    private void showBorrowedBooksReport() {
        System.out.println("\n==================================================");
        System.out.println("               BORROWED BOOKS REPORT              ");
        System.out.println("==================================================");
        System.out.println("Books that are currently borrowed by users");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-15s %-15s %-10s%n",
                "Record ID", "Student ID", "Book ID", "Borrow Date", "Expiry Date", "Status");
        System.out.println("--------------------------------------------------");

        String result = reportControl.getBorrowedBooksReport();
        System.out.println(result);

        pressEnterToContinue();
    }

    private void showOverdueBooksReport() {
        System.out.println("\n==================================================");
        System.out.println("                OVERDUE BOOKS REPORT              ");
        System.out.println("==================================================");
        System.out.println("Books that have passed the expiry date");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s %-12s %-10s %-15s %-15s %-10s%n",
                "Record ID", "Student ID", "Book ID", "Expiry Date", "Overdue Days", "Status");
        System.out.println("--------------------------------------------------");

        String result = reportControl.getOverdueBooksReport();
        System.out.println(result);

        pressEnterToContinue();
    }

    private void showMostBorrowedBooksReport() {
        System.out.println("\n==================================================");
        System.out.println("             MOST BORROWED BOOKS REPORT           ");
        System.out.println("==================================================");
        System.out.println("Book(s) with the highest borrowing frequency");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n",
                "Book ID", "Book Title", "Borrow Count");
        System.out.println("--------------------------------------------------");

        String result = reportControl.getMostBorrowedBooksReport();
        System.out.println(result);

        pressEnterToContinue();
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

    private void pressEnterToContinue() {
        System.out.println("--------------------------------------------------");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
