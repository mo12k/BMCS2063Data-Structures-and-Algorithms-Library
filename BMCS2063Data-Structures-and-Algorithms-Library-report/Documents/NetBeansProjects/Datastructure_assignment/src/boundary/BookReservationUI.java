//package boundary;
//
//import control.BookReservation;
//import java.util.Scanner;
//
//public class BookReservationUI {
//
//    private BookReservation reservationControl;
//    private Scanner scanner;
//
//    public BookReservationUI(BookReservation reservationControl) {
//        this.reservationControl = reservationControl;
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void start() {
//        int choice;
//
//        do {
//            
//
//            switch (choice) {
//                case 1:
//                    runStudentMenu();
//                    break;
//                case 2:
//                    runStaffMenu();
//                    break;
//                case 0:
//                    System.out.println("Exiting Reservation Module...");
//                    break;
//                default:
//                    System.out.println("Invalid choice.");
//            }
//
//        } while (choice != 0);
//    }
//
//    private void runStudentMenu() {
//        int choice;
//
//        do {
//            choice = displayStudentMenu();
//
//            switch (choice) {
//                case 1:
//                    reserveBook();
//                    break;
//                case 2:
//                    cancelReservation();
//                    break;
//                case 0:
//                    System.out.println("Back to previous menu...");
//                    break;
//                default:
//                    System.out.println("Invalid choice.");
//            }
//
//        } while (choice != 0);
//    }
//
//    private void runStaffMenu() {
//        int choice;
//
//        do {
//            choice = displayStaffMenu();
//
//            switch (choice) {
//                case 1:
//                    reserveBook();
//                    break;
//                case 2:
//                    cancelReservation();
//                    break;
//                case 3:
//                    viewWaitingList();
//                    break;
//                case 4:
//                    notifyNextStudent();
//                    break;
//                case 5:
//                    notifyDelay();
//                    break;
//                case 6:
//                    notifyBookRemoval();
//                    break;
//                case 0:
//                    System.out.println("Back to previous menu...");
//                    break;
//                default:
//                    System.out.println("Invalid choice.");
//            }
//
//        } while (choice != 0);
//    }
//
//    private int displayStudentMenu() {
//        System.out.println("\n==================================================");
//        System.out.println("                   STUDENT MENU                   ");
//        System.out.println("==================================================");
//        System.out.println("1. Reserve Book");
//        System.out.println("2. Cancel Reservation");
//        System.out.println("0. Back");
//        System.out.println("--------------------------------------------------");
//        System.out.print("Enter choice: ");
//        return readInt();
//    }
//
//    private int displayStaffMenu() {
//        System.out.println("\n==================================================");
//        System.out.println("                    STAFF MENU                    ");
//        System.out.println("==================================================");
//        System.out.println("1. Reserve Book");
//        System.out.println("2. Cancel Reservation");
//        System.out.println("3. View Waiting List");
//        System.out.println("4. Notify Next Student");
//        System.out.println("5. Notify Delay");
//        System.out.println("6. Notify Book Removal");
//        System.out.println("0. Back");
//        System.out.println("--------------------------------------------------");
//        System.out.print("Enter choice: ");
//        return readInt();
//    }
//
//    private void reserveBook() {
//        System.out.print("Enter Student ID: ");
//        String studentID = scanner.nextLine().trim();
//
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        System.out.print("Enter Student Name: ");
//        String studentName = scanner.nextLine().trim();
//
//        String result = reservationControl.reserveBook(studentID, bookID, studentName);
//        System.out.println(result);
//        pause();
//    }
//
//    private void cancelReservation() {
//        System.out.print("Enter Student ID: ");
//        String studentID = scanner.nextLine().trim();
//
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        String result = reservationControl.cancelReservation(studentID, bookID);
//        System.out.println(result);
//        pause();
//    }
//
//    private void viewWaitingList() {
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        String result = reservationControl.viewWaitingList(bookID);
//        System.out.println(result);
//        pause();
//    }
//
//    private void notifyNextStudent() {
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        String result = reservationControl.notifyNextStudent(bookID);
//        System.out.println(result);
//        pause();
//    }
//
//    private void notifyDelay() {
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        String result = reservationControl.notifyDelay(bookID);
//        System.out.println(result);
//        pause();
//    }
//
//    private void notifyBookRemoval() {
//        System.out.print("Enter Book ID: ");
//        String bookID = scanner.nextLine().trim().toUpperCase();
//
//        String result = reservationControl.notifyBookRemoval(bookID);
//        System.out.println(result);
//        pause();
//    }
//
//    private int readInt() {
//        while (!scanner.hasNextInt()) {
//            System.out.print("Invalid input. Please enter a number: ");
//            scanner.next();
//        }
//        int value = scanner.nextInt();
//        scanner.nextLine();
//        return value;
//    }
//
//    private void pause() {
//        System.out.println("--------------------------------------------------");
//        System.out.print("Press Enter to continue...");
//        scanner.nextLine();
//    }
//}