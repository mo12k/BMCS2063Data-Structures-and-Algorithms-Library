package control;

import adt.DoublyLinkedList;
import adt.ListInterface;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import entity.Book;
import entity.Reservation;
import entity.BorrowRecord;

public class BookReservation {

    private ListInterface<Book> bookList;
    private ListInterface<Reservation> reservationList;

    private BookDAO bookDAO;
    private BorrowRecordDAO borrowRecordDAO;
    private ListInterface<BorrowRecord> borrowRecordList;

    private static int reservationCount = 1;

    public BookReservation() {
        bookDAO = new BookDAO();
        borrowRecordDAO = new BorrowRecordDAO();

        bookList = bookDAO.retrieveFromFile();
        reservationList = new DoublyLinkedList<>();
        borrowRecordList = borrowRecordDAO.retrieveFromFile();

        if (bookList.isEmpty()) {
            bookDAO.saveToFile(bookList);
        }
    }

    private String generateReservationID() {
        return String.format("RS%03d", reservationCount++);
    }

    private Book findBookById(String bookID) {
        for (int i = 1; i <= bookList.size(); i++) {
            Book b = bookList.get(i);
            if (b != null && b.getBookID().equalsIgnoreCase(bookID)) {
                return b;
            }
        }
        return null;
    }

    private Reservation findReservation(String studentID, String bookID) {
        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getStudentID().equalsIgnoreCase(studentID)
                    && r.getBook().getBookID().equalsIgnoreCase(bookID)
                    && !"Cancelled".equalsIgnoreCase(r.getStatus())
                    && !"Book Removed".equalsIgnoreCase(r.getStatus())) {
                return r;
            }
        }
        return null;
    }

    private boolean hasDuplicateReservation(String studentID, String bookID) {
        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getStudentID().equalsIgnoreCase(studentID)
                    && r.getBook().getBookID().equalsIgnoreCase(bookID)
                    && !"Cancelled".equalsIgnoreCase(r.getStatus())
                    && !"Book Removed".equalsIgnoreCase(r.getStatus())
                    && !"Notified".equalsIgnoreCase(r.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }

    // 🔥 找 BorrowRecord name
    private String findStudentNameFromBorrowRecord(String studentID) {
        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord r = borrowRecordList.get(i);

            if (r != null
                    && r.getBorrowerID().equalsIgnoreCase(studentID)
                    && r.getBorrowName() != null
                    && !r.getBorrowName().isEmpty()) {
                return r.getBorrowName();
            }
        }
        return null;
    }

    // 🔥 user input name
    private String promptStudentName() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String name;

        do {
            System.out.print("Enter student name: ");
            name = sc.nextLine().trim();
        } while (name.isEmpty());

        return name;
    }

    // =========================
    // ✅ RESERVE BOOK (重點)
    // =========================
    public String reserveBook(String studentID, String bookID) {

        Book book = findBookById(bookID);
        if (book == null) {
            return "Book not found.";
        }

        if (book.isIsAvailable()) {
            return "Book is available. No need reserve.";
        }

        if (hasDuplicateReservation(studentID, bookID)) {
            return "Already reserved.";
        }

        // 🔥 找名字
        String studentName = findStudentNameFromBorrowRecord(studentID);

        if (studentName == null) {
            studentName = promptStudentName();
        }

        // 🔥 waiting list 改 String
        book.getWaitingList().add(studentID + " - " + studentName);

        Reservation r = new Reservation(
                generateReservationID(),
                book,
                studentID,
                studentName,
                getCurrentDate(),
                "Active"
        );

        reservationList.add(r);
        bookDAO.saveToFile(bookList);

        return "Reservation success for " + studentName;
    }

    // =========================
    // CANCEL
    // =========================
    public String cancelReservation(String studentID, String bookID) {

        Book book = findBookById(bookID);
        if (book == null) return "Book not found";

        Reservation r = findReservation(studentID, bookID);
        if (r == null) return "No reservation";

        for (int i = 1; i <= book.getWaitingList().size(); i++) {
            String s = book.getWaitingList().get(i);

            if (s.startsWith(studentID)) {
                book.getWaitingList().remove(i);
                break;
            }
        }

        r.setStatus("Cancelled");
        bookDAO.saveToFile(bookList);

        return "Cancelled";
    }

    // =========================
    // VIEW WAITING LIST
    // =========================
    public String viewWaitingList(String bookID) {

        Book book = findBookById(bookID);
        if (book == null) return "Book not found";

        if (book.getWaitingList().isEmpty()) {
            return "Empty waiting list";
        }

        String output = "";

        for (int i = 1; i <= book.getWaitingList().size(); i++) {
            output += i + ". " + book.getWaitingList().get(i) + "\n";
        }

        return output;
    }

    // =========================
    // NOTIFY NEXT
    // =========================
    public String notifyNextStudent(String bookID) {

        Book book = findBookById(bookID);
        if (book == null) return "Book not found";

        if (book.getWaitingList().isEmpty()) return "No waiting";

        String entry = book.getWaitingList().get(1);
        book.getWaitingList().remove(1);

        String studentID = entry.split(" - ")[0];

        Reservation r = findReservation(studentID, bookID);
        if (r != null) {
            r.setStatus("Notified");
        }

        return "Notified: " + entry;
    }
    
    public String notifyBookRemoval(String bookID) {

    Book book = findBookById(bookID);
    if (book == null) return "Book not found";

    if (book.getWaitingList().isEmpty()) {
        return "No waiting list";
    }

    int count = book.getWaitingList().size();

    for (int i = 1; i <= count; i++) {

        String entry = book.getWaitingList().get(i);
        String studentID = entry.split(" - ")[0];

        Reservation r = findReservation(studentID, bookID);

        if (r != null) {
            r.setStatus("Book Removed");
        }
    }

    // 🔥 清空 waiting list
    book.getWaitingList().clear();

    bookDAO.saveToFile(bookList);

    return count + " students notified. Book removed.";
}
    
    public String notifyDelay(String bookID) {

    Book book = findBookById(bookID);
    if (book == null) return "Book not found";

    if (book.getWaitingList().isEmpty()) {
        return "No waiting list";
    }

    // 取得第一個人（不移除）
    String entry = book.getWaitingList().get(1);

    String studentID = entry.split(" - ")[0];

    Reservation r = findReservation(studentID, bookID);
    if (r != null) {
        r.setStatus("Delayed");
    }

    return "Delay notification sent to: " + entry;
}
}