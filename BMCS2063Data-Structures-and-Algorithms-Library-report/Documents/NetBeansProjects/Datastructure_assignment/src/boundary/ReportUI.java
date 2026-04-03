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
import utility.UITools;

public class ReportUI {

    private Scanner scanner = new Scanner(System.in);
    private ReportManagement reportControl = new ReportManagement();

    public void startReportModule() {
        int choice;

        do {
            showMainHeader();
            choice = UITools.readInt();

            switch (choice) {
                case 1:
                    showMostBorrowedBooksReport();
                    break;
                case 2:
                    showMostActiveBorrowersReport();
                    break;
                case 3:
                    showMostUnpaidFineReport();
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
        System.out.println("1. Top 5 Borrowed Books Report");
        System.out.println("2. Top 5 Active Borrowers Report");
        System.out.println("1. Top 5 Unpaid fine Borrowers Report");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
    }

    private void showMostBorrowedBooksReport() {
        System.out.println("\n===================================================================");
        System.out.println("             TOP 5 BORROWED BOOKS REPORT           ");
        System.out.println("===================================================================");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n",
                "Book ID", "Book Title", "Borrow Count");
        System.out.println("-------------------------------------------------------------------");

        String result = reportControl.getTop5MostBorrowedBooksReport();
        System.out.println(result);

        UITools.pressEnterToContinue();
    }
    
    private void showMostActiveBorrowersReport() {
        System.out.println("\n===================================================================");
        System.out.println("           TOP 5 ACTIVE BORROWERS REPORT             ");
        System.out.println("===================================================================");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n",
                "Book ID", "Book Title", "Borrow Count");
        System.out.println("-------------------------------------------------------------------");

        String result = reportControl.getTop5MostActiveBorrowersReport();
        System.out.println(result);

        UITools.pressEnterToContinue();
    }
    
    private void showMostUnpaidFineReport() {
        System.out.println("\n===================================================================");
        System.out.println("        TOP 5 UNPAID FINE BORROWERS REPORT        ");
        System.out.println("===================================================================");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n",
                "Book ID", "Book Title", "Borrow Count");
        System.out.println("-------------------------------------------------------------------");

        String result = reportControl.getTop5MostUnpaidFineBorrowersReport();
        System.out.println(result);

        UITools.pressEnterToContinue();
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
