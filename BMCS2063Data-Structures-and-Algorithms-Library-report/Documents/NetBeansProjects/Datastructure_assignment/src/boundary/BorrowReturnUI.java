package boundary;



/**
 *
 * @author lee
 */


import control.BorrowReturnBook;
import control.FineManagement;
import entity.Book;
import entity.Fine;
import utility.UITools;
import java.util.Scanner;

public class BorrowReturnUI {

    private Scanner scanner = new Scanner(System.in);
    private BorrowReturnBook control;

    public BorrowReturnUI(){
        this.control = new BorrowReturnBook();
    }

    public BorrowReturnUI(control.BookReservation reservationControl){
        this.control = new BorrowReturnBook(reservationControl);
    }

    public void startBorrowReturnModule() {
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
                    System.out.println("Exiting Borrow & Return Book Module...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void startStaffModule() {
        runStaffMenu();
    }

    public void startStudentModule() {
        runStudentMenu();
    }

    private int getMainMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("             BORROW & RETURN MODULE               ");
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
                    displayBorrowedBooks();
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
                    searchBookAndBorrow();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewWaitingList();
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
        System.out.println("1. View Borrow Records by Status");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private int getStudentMenuChoice() {
        System.out.println("\n==================================================");
        System.out.println("                   STUDENT MENU                   ");
        System.out.println("==================================================");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. View Waiting List");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");
        return UITools.readInt();
    }

    private void searchBookAndBorrow() {
        System.out.println("\n==================================================");
        System.out.println("                    BORROW BOOK                   ");
        System.out.println("==================================================");

        String uncorrectStudentId = inputBorrowerId();
        String studentId = uncorrectStudentId.toUpperCase();

        if (!control.isValidStudentId(studentId)) {
            System.out.println("Invalid Student ID format. Returning to menu...");
            return;
        }

        String studentName = control.findStudentNameById(studentId);

        if (studentName == null) {
            System.out.print("First time user, please enter Student Name: ");
            studentName = scanner.nextLine().trim();
        }

        String keyword = inputSearchKeyword();

        System.out.println("\nSearch Results:");
        String resultBook = control.searchBook(keyword);

        if (resultBook == null || resultBook.trim().isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        System.out.println(resultBook);

        String bookIdInput = inputBookId();
        String bookId = bookIdInput.toUpperCase();

        Book book = control.findBookById(bookId);

        if (book == null) {
            System.out.println("Book ID not found.");
            return;
        }

        if (control.checkAvailability(bookId)) {
            System.out.println("Book is available.");
            System.out.print("Proceed to borrow? (Y/N): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                int result = control.borrowBook(studentId, bookId, studentName);

                switch (result) {
                    case 1:
                        System.out.println("Book borrowed successfully.");
                        break;
                    case -1:
                        System.out.println("Invalid student ID.");
                        break;
                    case -2:
                        System.out.println("Book not found.");
                        break;
                    case -3:
                        System.out.println("You already borrowed this book.");
                        break;
                    case -4:
                        System.out.println("Book is not available.");
                        break;
                    default:
                        System.out.println("Borrow failed.");
                }
            } else {
                System.out.println("Borrow cancelled.");
            }

        } else {
            System.out.println("Book is not available.");
            System.out.print("Do you want to join the waiting list? (Y/N): ");
            String joinWaitList = scanner.nextLine().trim();

            if (joinWaitList.equalsIgnoreCase("Y")) {
                boolean joined = control.addToWaitingList(studentId, bookId, studentName);

                if (joined) {
                    System.out.println("You have been added to the waiting list.");
                } else {
                    System.out.println("Failed to join waiting list.");
                }
            } else {
                System.out.println("Waiting list request cancelled.");
            }
        }

        UITools.pressEnterToContinue();
    }

    private void returnBook() {
        System.out.println("\n==================================================");
        System.out.println("                    RETURN BOOK                   ");
        System.out.println("==================================================");

        String uncorrectStudentId = inputBorrowerId();
        String studentId = uncorrectStudentId.toUpperCase();

        if (!control.isValidStudentId(studentId)) {
            System.out.println("Invalid Student ID format. Returning to menu...");
            return;
        }

        String records = control.getActiveBorrowRecordStringByStudent(studentId);

        if (records == null || records.trim().isEmpty()) {
            System.out.println("No active borrowed records found for this student.");
            return;
        }

        System.out.println("\nYour Active Borrowed Records:");
        System.out.println(records);

        String bookIdInput = inputBookId();
        String bookId = bookIdInput.toUpperCase();

        boolean success = control.returnBook(studentId, bookId);

        if (success) {
            System.out.println("Book returned successfully.");
            handleFineAfterReturn(recordID);
        } else {
            System.out.println("Return failed. Record not found.");
        }

        UITools.pressEnterToContinue();
    }

    private void viewWaitingList() {
    
        System.out.println("\n==================================================");
        System.out.println("                 VIEW WAITING LIST                ");
        System.out.println("==================================================");

        String keyword = inputSearchKeyword();

        System.out.println("\nSearch Results:");
        String resultBook = control.searchBookForWaitingList(keyword);

        if (resultBook == null || resultBook.trim().isEmpty()) {
            System.out.println("No matching books found.");
            UITools.pressEnterToContinue();
            return;
        }

        System.out.println(resultBook);

        String bookIdInput = inputBookId();
        String bookId = bookIdInput.toUpperCase();

        String waitingListResult = control.viewWaitingList(bookId);
        System.out.println(waitingListResult);

        UITools.pressEnterToContinue();
    }

    private void displayBorrowedBooks() {
        System.out.println("\n==================================================");
        System.out.println("               BORROW RECORDS STATUS              ");
        System.out.println("==================================================");
        System.out.println("1. BORROWED");
        System.out.println("2. RETURNED");
        System.out.println("3. EXPIRED");
        System.out.println("0. Back");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter choice: ");

        int choice = UITools.readInt();

        String status = "";

        switch (choice) {
            case 1:
                status = "BORROWED";
                break;
            case 2:
                status = "RETURNED";
                break;
            case 3:
                status = "EXPIRED";
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        String output = control.getRecordsByStatus(status);

        System.out.println("\n--------------------------------------------------");
        System.out.println("Status: " + status);
        System.out.println("--------------------------------------------------");

        if (output == null || output.trim().isEmpty()) {
            System.out.println("No records found for status: " + status);
        } else {
            System.out.println(output);
        }

        UITools.pressEnterToContinue();
    }

    private String inputBorrowerId() {
        System.out.print("Enter Student ID: ");
        return scanner.nextLine().trim();
    }

    private String inputBookId() {
        System.out.print("Enter Book ID: ");
        return scanner.nextLine().trim();
    }

    private String inputSearchKeyword() {
        System.out.print("Enter Book Title / Author / Category keyword: ");
        return scanner.nextLine().trim();
    }
    
    //Yang
    private void handleFineAfterReturn(String recordID) {
        FineManagement fineControl = new FineManagement();

        Fine fines = fineControl.findUnpaidFine(recordID);

        if (fines == null) {
            return;
        }

        System.out.println("\nYou have unpaid fines:");
        System.out.println("Fine ID    : " + fines.getFineID());
        System.out.println("Student ID : " + fines.getBorrowRecord().getBorrowerID());
        System.out.println("Book ID    : " + fines.getBorrowRecord().getBookID());
        System.out.println("Amount     : RM " + String.format("%.2f", fines.getAmount()));
        System.out.println("Status     : " + fines.getStatus());
        System.out.println("Overdue    : " + fines.getOverdueDays() + " day(s)");

        System.out.print("Do you want to pay now? (Y/N): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.print("Enter Fine ID: ");
            String fineID = scanner.nextLine();

            String result = fineControl.payFine(fineID);
            System.out.println(result);
        } else {
            System.out.println("You can pay later in Fine Management.");
        }
    }
    
}