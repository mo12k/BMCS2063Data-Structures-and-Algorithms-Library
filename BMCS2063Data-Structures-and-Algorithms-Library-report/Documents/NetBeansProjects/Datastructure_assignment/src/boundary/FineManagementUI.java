package boundary;

import control.BorrowReturnBook;
import control.FineManagement;
import java.util.Scanner;
import utility.UITools;
public class FineManagementUI {

    private Scanner scanner = new Scanner(System.in);
    private FineManagement fineControl = new FineManagement();
    private BorrowReturnBook borrowControl = new BorrowReturnBook();

    public void startFineManagementModule() {
        borrowControl.refreshExpiredStatus();
        fineControl.autoGenerateFines(borrowControl.getAllRecords());

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
                    System.out.println("\nExiting Fine Management Module...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void startStaffModule() {
        borrowControl.refreshExpiredStatus();
        fineControl.autoGenerateFines(borrowControl.getAllRecords());
        runStaffMenu();
    }

    public void startStudentModule() {
        borrowControl.refreshExpiredStatus();
        fineControl.autoGenerateFines(borrowControl.getAllRecords());
        runStudentMenu();
    }

    private int getMainMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("               FINE MANAGEMENT MODULE             ");
        System.out.println("==================================================");
        System.out.println("1. Staff");
        System.out.println("2. Student");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
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
                    System.out.println("\nBack to previous menu...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
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
                    System.out.println("\nBack to previous menu...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private int getStaffMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("                    STAFF MENU                    ");
        System.out.println("==================================================");
        System.out.println("1. View Unpaid Fines");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private int getStudentMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("                   STUDENT MENU                   ");
        System.out.println("==================================================");
        System.out.println("1. View My Fine History");
        System.out.println("2. Pay Fine");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private void displayUnpaidFines() {
        System.out.println("\n==================================================");
        System.out.println("                  UNPAID FINES                    ");
        System.out.println("==================================================");
        System.out.printf("%-7s | %-10s | %-7s | %-8s | %-10s%n",
                "Fine ID", "Student ID", "Book ID", "Amount", "Overdue");
        System.out.println("--------------------------------------------------");

        String result = fineControl.displayUnpaidFines();
        System.out.println(result);

       UITools.pressEnterToContinue();
    }

    private void payFine() {
        System.out.println("\n==================================================");
        System.out.println("                     PAY FINE                     ");
        System.out.println("==================================================");

        String fineID = inputFineID();
        String result = fineControl.payFine(fineID);

        System.out.println("--------------------------------------------------");
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void viewStudentFineHistory() {
        System.out.println("\n==================================================");
        System.out.println("                MY FINE HISTORY                   ");
        System.out.println("==================================================");

        String studentID = inputStudentID();

        System.out.printf("%-7s | %-8s | %-10s%n",
                "Fine ID", "Amount", "Status");
        System.out.println("--------------------------------------------------");

        String result = fineControl.viewStudentFines(studentID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private String inputStudentID() {
        System.out.print("Enter Student ID: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    private String inputFineID() {
        System.out.print("Enter Fine ID: ");
        return scanner.nextLine().trim().toUpperCase();
    }

}