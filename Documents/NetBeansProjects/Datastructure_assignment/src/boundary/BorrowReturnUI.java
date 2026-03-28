///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package boundary;
//
//import control.*;
//import entity.Book;
//import java.util.Scanner;
//
//public class BorrowReturnUI {
//
//    private Scanner scanner = new Scanner(System.in);
//    private BorrowReturnBook control = new BorrowReturnBook();
//
//    public void startBorrowReturnModule() {
//        int choice;
//
//        do {
//            choice = getMainMenuChoice();
//
//            switch (choice) {
//                case 1:
//                    runStaffMenu();
//                    break;
//                case 2:
//                    runStudentMenu();
//                    break;
//                case 0:
//                    System.out.println("Exiting Borrow & Return Book Module...");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 0);
//    }
//
//    private int getMainMenuChoice() {
//        System.out.println("\n======================================");
//        System.out.println("     BORROW & RETURN BOOK MODULE");
//        System.out.println("======================================");
//        System.out.println("1. Staff");
//        System.out.println("2. Student");
//        System.out.println("0. Exit");
//        System.out.print("Enter choice: ");
//
//        return readInt();
//    }
//
//    private void runStaffMenu() {
//        int choice;
//
//        do {
//            choice = getStaffMenuChoice();
//
//            switch (choice) {
//                case 1:
//                    searchBookAndBorrow("Staff");
//                    break;
//                case 2:
//                    returnBook("Staff");
//                    break;
//                case 3:
//                    displayBorrowedBooks();
//                    break;
//                case 0:
//                    System.out.println("Back to main menu...");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 0);
//    }
//
//    private void runStudentMenu() {
//        int choice;
//
//        do {
//            choice = getStudentMenuChoice();
//
//            switch (choice) {
//                case 1:
//                    searchBookAndBorrow("Student");
//                    break;
//                case 2:
//                    returnBook("Student");
//                    break;
//                case 3:
//                    displayBorrowedBooks();
//                    break;
//                case 0:
//                    System.out.println("Back to main menu...");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 0);
//    }
//
//    private int getStaffMenuChoice() {
//        System.out.println("\n--------------------------------------");
//        System.out.println("              STAFF MENU");
//        System.out.println("--------------------------------------");
//        System.out.println("1. Search Book and Borrow");
//        System.out.println("2. Return Book");
//        System.out.println("3. Display Borrowed Books");
//        System.out.println("0. Back");
//        System.out.print("Enter choice: ");
//
//        return readInt();
//    }
//
//    private int getStudentMenuChoice() {
//        System.out.println("\n--------------------------------------");
//        System.out.println("             STUDENT MENU");
//        System.out.println("--------------------------------------");
//        System.out.println("1. Search Book and Borrow");
//        System.out.println("2. Return Book");
//        System.out.println("3. Display Borrowed Books");
//        System.out.println("0. Back");
//        System.out.print("Enter choice: ");
//
//        return readInt();
//    }
//
//    private void searchBookAndBorrow(String expectedRole) {
//        System.out.println("\n========== SEARCH BOOK AND BORROW ==========");
//
//        String borrowerId = inputBorrowerId();
//        String keyword = inputSearchKeyword();
//
//        System.out.println("\nSearch Results:");
//        String result = control.searchBook(keyword);
//
//        if (result == null || result.trim().isEmpty()) {
//            System.out.println("No matching books found.");
//            return;
//        }
//
//        System.out.println(result);
//
//        String bookId = inputBookId();
//
//        Book book = control.findBookById(bookId);
//
//        if (book == null) {
//            System.out.println("Book ID not found.");
//            return;
//        }
//
//     
//
//        if (control.checkAvailability(bookId)) {
//            System.out.println("Book is available.");
//            System.out.print("Proceed to borrow? (Y/N): ");
//            String confirm = scanner.nextLine();
//
//            if (confirm.equalsIgnoreCase("Y")) {
//                boolean success = control.borrowBook(borrowerId, bookId);
//
//                if (success) {
//                    System.out.println("Book borrowed successfully.");
//                } else {
//                    System.out.println("Borrow failed.");
//                }
//            } else {
//                System.out.println("Borrow cancelled.");
//            }
//        } else {
//            System.out.println("Book is not available.");
//        }
//    }
//
//    private void returnBook(String expectedRole) {
//        System.out.println("\n=============== RETURN BOOK ===============");
//
//        String borrowerId = inputBorrowerId();
//        String bookId = inputBookId();
//
//        
//
//        boolean success = control.returnBook(borrowerId, bookId);
//
//        if (success) {
//            System.out.println("Book returned successfully.");
//        } else {
//            System.out.println("Return failed. Record not found or already returned.");
//        }
//    }
//
//    private void displayBorrowedBooks() {
//        System.out.println("\n=========== BORROWED BOOK LIST ===========");
//
//        String output = control.getAllBorrowedBooks();
//
//        if (output == null || output.trim().isEmpty()) {
//            System.out.println("No borrowed books found.");
//        } else {
//            System.out.println(output);
//        }
//    }
//
//    private String inputBorrowerId() {
//        System.out.print("Enter Borrower ID: ");
//        return scanner.nextLine().trim();
//    }
//
//    private String inputBookId() {
//        System.out.print("Enter Book ID: ");
//        return scanner.nextLine().trim();
//    }
//
//    private String inputSearchKeyword() {
//        System.out.print("Enter Book Title / Author / Category keyword: ");
//        return scanner.nextLine().trim();
//    }
//
//    private int readInt() {
//        while (!scanner.hasNextInt()) {
//            System.out.print("Invalid input. Please enter a number: ");
//            scanner.next();
//        }
//        int value = scanner.nextInt();
//        scanner.nextLine(); // clear buffer
//        return value;
//    }
//}
