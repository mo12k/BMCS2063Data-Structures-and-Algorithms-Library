package boundary;

import control.BookMaintenance;
import java.util.Scanner;

public class LibrarySystemUI {

    private Scanner scanner = new Scanner(System.in);

    private BookMaintenance bookMaintenance = new BookMaintenance();
    private BookReservationUI bookReservationUI = new BookReservationUI();
    private BorrowReturnUI borrowReturnUI = new BorrowReturnUI();
    private FineManagementUI fineManagementUI = new FineManagementUI();
    private ReportUI reportUI = new ReportUI();

    public void startSystem() {
        int choice;

        do {
            choice = getMainMenuChoice();

            switch (choice) {
                case 1:
                    runStaffPortal();
                    break;
                case 2:
                    runStudentPortal();
                    break;
                case 0:
                    System.out.println("\nExiting Library System...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private int getMainMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("                 TAR UMT LIBRARY SYSTEM           ");
        System.out.println("==================================================");
        System.out.println("1. Staff Portal");
        System.out.println("2. Student Portal");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return readInt();
    }

    private void runStaffPortal() {
        int choice;

        do {
            choice = getStaffPortalChoice();

            switch (choice) {
                case 1:
                    bookMaintenance.runStaffMenu();
                    break;
                case 2:
                    borrowReturnUI.startStaffModule();
                    break;
                case 3:
                    bookReservationUI.startStaffModule();
                    break;
                case 4:
                    fineManagementUI.startStaffModule();
                    break;
                case 5:
                    reportUI.startReportModule();
                    break;
                case 0:
                    System.out.println("\nBack to Main Menu...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void runStudentPortal() {
        int choice;

        do {
            choice = getStudentPortalChoice();

            switch (choice) {
                case 1:
                    bookMaintenance.runStudentMenu();
                    break;
                case 2:
                    borrowReturnUI.startStudentModule();
                    break;
                case 3:
                    bookReservationUI.startStudentModule();
                    break;
                case 4:
                    fineManagementUI.startStudentModule();
                    break;
                case 0:
                    System.out.println("\nBack to Main Menu...");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private int getStaffPortalChoice() {
        System.out.println("\n==================================================");
        System.out.println("                    STAFF PORTAL                  ");
        System.out.println("==================================================");
        System.out.println("1. Book Maintenance");
        System.out.println("2. Borrow / Return Management");
        System.out.println("3. Book Reservation & Waiting List");
        System.out.println("4. Fine Management");
        System.out.println("5. Report Module");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return readInt();
    }

    private int getStudentPortalChoice() {
        System.out.println("\n==================================================");
        System.out.println("                   STUDENT PORTAL                 ");
        System.out.println("==================================================");
        System.out.println("1. Search / Display Books");
        System.out.println("2. Borrow / Return Books");
        System.out.println("3. Reserve Books");
        System.out.println("4. Fine Management");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return readInt();
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

    public static void main(String[] args) {
        LibrarySystemUI systemUI = new LibrarySystemUI();
        systemUI.startSystem();
    }
}