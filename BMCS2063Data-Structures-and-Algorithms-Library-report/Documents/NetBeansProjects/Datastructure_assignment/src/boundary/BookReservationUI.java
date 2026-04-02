/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package boundary;

/**
 *
 * @author lamzh
 */

import control.BookReservation;
import java.util.Scanner;

public class BookReservationUI {

    private BookReservation reservationControl;
    private Scanner scanner;

    public BookReservationUI(BookReservation reservationControl) {
        this.reservationControl = reservationControl;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;

        do {
            displayMenu();
            choice = getChoice();

            switch (choice) {
                case 1 -> reserveBook(null,null,null);
                case 2 -> cancelReservation();
                case 3 -> viewWaitingList();
                case 4 -> notifyNextStudent();
                case 5 -> notifyDelay();
                case 6 -> notifyBookRemoval();
                case 0 -> System.out.println("Exiting Reservation Module...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    private void displayMenu() {
        System.out.println("\n=== Book Reservation Menu ===");
        System.out.println("1. Reserve Book");
        System.out.println("2. Cancel Reservation");
        System.out.println("3. View Waiting List");
        System.out.println("4. Notify Next Student");
        System.out.println("5. Notify Delay");
        System.out.println("6. Notify Book Removal");
        System.out.println("0. Exit");
    }

    private int getChoice() {
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }

    private void reserveBook(String studentID,String bookID,String studentName) {
        
        if(studentID ==null ||bookID ==null||studentName ==null){
        scanner.nextLine();
        System.out.print("Enter Student ID: ");
        studentID = scanner.nextLine();

        System.out.print("Enter Book ID: ");
        bookID = scanner.nextLine();

        String result = reservationControl.reserveBook(studentID, bookID,studentName);
        System.out.println(result);}
        else {
        String result2 = reservationControl.reserveBook(studentID, bookID,studentName);
        System.out.println(result2);}
    }

    private void cancelReservation() {
        scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();

        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine();

        String result = reservationControl.cancelReservation(studentID, bookID);
        System.out.println(result);
    }

    private void viewWaitingList() {
        scanner.nextLine();
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine();

        String result = reservationControl.viewWaitingList(bookID);
        System.out.println(result);
    }

    private void notifyNextStudent() {
        scanner.nextLine();
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine();

        String result = reservationControl.notifyNextStudent(bookID);
        System.out.println(result);
    }

    private void notifyDelay() {
        scanner.nextLine();
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine();

        String result = reservationControl.notifyDelay(bookID);
        System.out.println(result);
    }

    private void notifyBookRemoval() {
        scanner.nextLine();
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine();

        String result = reservationControl.notifyBookRemoval(bookID);
        System.out.println(result);
    }
}
