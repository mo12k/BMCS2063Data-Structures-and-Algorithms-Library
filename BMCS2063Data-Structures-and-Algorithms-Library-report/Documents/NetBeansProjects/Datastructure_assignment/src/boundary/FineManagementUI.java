/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

/**
 *
 * @author Yang
 */

import control.BorrowReturnBook;
import control.FineManagement;
import java.util.Scanner;

public class FineManagementUI {

    private Scanner scanner = new Scanner(System.in);
    private FineManagement fineControl = new FineManagement();
    private BorrowReturnBook borrowControl = new BorrowReturnBook();

    public void startFineManagementModule() {
        int choice;

        do {
            choice = getMainMenuChoice();

            switch (choice) {
                case 1:
                    runStaffMenu();
                    break;
                case 2:
                    runStudentMenu();
                    break;
                case 0:
                    System.out.println("Exiting Fine Management Module...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private int getMainMenuChoice() {
        System.out.println("\n======================================");
        System.out.println("        FINE MANAGEMENT MODULE");
        System.out.println("======================================");
        System.out.println("1. Staff");
        System.out.println("2. Student");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
        return readInt();
    }

    private void runStaffMenu() {
        int choice;

        do {
            choice = getStaffMenuChoice();

            switch (choice) {
                case 1:
                    displayUnpaidFines();
                    break;
                case 0:
                    System.out.println("Back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void runStudentMenu() {
        int choice;

        do {
            choice = getStudentMenuChoice();

            switch (choice) {
                case 1:
                    viewStudentFineHistory();
                    break;
                case 2:
                    payFine();
                    break;
                case 0:
                    System.out.println("Back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private int getStaffMenuChoice() {
        System.out.println("\n--------------------------------------");
        System.out.println("              STAFF MENU");
        System.out.println("--------------------------------------");
        System.out.println("1. View Unpaid Fines");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        return readInt();
    }

    private int getStudentMenuChoice() {
        System.out.println("\n--------------------------------------");
        System.out.println("             STUDENT MENU");
        System.out.println("--------------------------------------");
        System.out.println("1. View My Fine History");
        System.out.println("2. Pay Fine");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        return readInt();
    }

    private void displayUnpaidFines() {
        System.out.println("\n========== Unpaid Fines ==========");
        String result = fineControl.displayUnpaidFines();
        System.out.println(result);
    }

    private void payFine() {
        System.out.println("\n========== Pay Fine ==========");
        String fineID = inputFineID();
        String result = fineControl.payFine(fineID);
        System.out.println(result);
    }

    private void viewStudentFineHistory() {
        System.out.println("\n========== Fine History ==========");
        String studentID = inputStudentID();
        String result = fineControl.viewStudentFines(studentID);
        System.out.println(result);
    }

    private String inputStudentID() {
        System.out.print("Enter Student ID: ");
        return scanner.nextLine().trim();
    }

    private String inputFineID() {
        System.out.print("Enter Fine ID: ");
        return scanner.nextLine().trim();
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