package boundary;

import control.BookReservation;
import utility.UITools;
import java.util.Scanner;

public class BookReservationUI {

    private Scanner scanner = new Scanner(System.in);
    private BookReservation reservationControl;

    public BookReservationUI() {
        this.reservationControl = new BookReservation();
    }

    public BookReservationUI(BookReservation reservationControl) {
        this.reservationControl = reservationControl;
    }

    public void startStaffModule() {
        runStaffMenu();
    }

    public void startStudentModule() {
        runStudentMenu();
    }

    private void runStaffMenu() {
        int choice;

        do {
            choice = getStaffMenuChoice();

            switch (choice) {
                case 1:
                    viewWaitingList();
                    break;
                case 2:
                    notifyNextStudent();
                    break;
                case 3:
                    notifyDelay();
                    break;
                case 4:
                    notifyBookRemoval();
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
                    reserveBook();
                    break;
                case 2:
                    cancelReservation();
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
        System.out.println("1. View Waiting List");
        System.out.println("2. Notify Next Student");
        System.out.println("3. Notify Delay");
        System.out.println("4. Notify Book Removal");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private int getStudentMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("                   STUDENT MENU                   ");
        System.out.println("==================================================");
        System.out.println("1. Reserve Book");
        System.out.println("2. Cancel Reservation");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private void reserveBook() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.reserveBook(studentID, bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void cancelReservation() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.cancelReservation(studentID, bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void viewWaitingList() {
        System.out.println("\n==================================================");
        System.out.println("                 VIEW WAITING LIST                ");
        System.out.println("==================================================");

        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.viewWaitingList(bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void notifyNextStudent() {
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.notifyNextStudent(bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void notifyDelay() {
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.notifyDelay(bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

    private void notifyBookRemoval() {
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim().toUpperCase();

        String result = reservationControl.notifyBookRemoval(bookID);
        System.out.println(result);

        UITools.pressEnterToContinue();
    }

}