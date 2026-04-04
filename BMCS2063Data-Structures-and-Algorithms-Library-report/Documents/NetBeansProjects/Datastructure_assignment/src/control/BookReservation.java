/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package control;

/**
 *
 * @author lamzh
 */

import adt.DoublyLinkedList;
import adt.ListInterface;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.ReservationDAO;
import entity.Book;
import entity.BorrowRecord;
import entity.Student;
import entity.Reservation;

public class BookReservation {

    private ListInterface<Book> bookList;
    private ListInterface<Student> studentList;
    private ListInterface<Reservation> reservationList;
    private ListInterface<BorrowRecord> borrowRecordList;

    private BorrowRecordDAO borrowRecordDAO;
    private BookDAO bookDAO;
    private ReservationDAO reservationDAO;

    private static int reservationCount = 1;

    public BookReservation() {
        bookDAO = new BookDAO();
        reservationDAO = new ReservationDAO();
        borrowRecordDAO = new BorrowRecordDAO();

        bookList = bookDAO.retrieveFromFile();
        studentList = new DoublyLinkedList<>();
        reservationList = reservationDAO.retrieveFromFile();
        borrowRecordList = borrowRecordDAO.retrieveFromFile();

        syncNextReservationId();

        if (bookList.isEmpty()) {
            bookDAO.saveToFile(bookList);
        }
    }

    private void reloadData() {
        bookList = bookDAO.retrieveFromFile();
        reservationList = reservationDAO.retrieveFromFile();
        borrowRecordList = borrowRecordDAO.retrieveFromFile();
    }

    private void syncNextReservationId() {
        int maxId = 0;

        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null && r.getReservationID() != null) {
                String id = r.getReservationID().trim();

                if (id.length() >= 2 && id.charAt(0) == 'R') {
                    try {
                        int number = Integer.parseInt(id.substring(1));
                        if (number > maxId) {
                            maxId = number;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }

        reservationCount = maxId + 1;
    }

    private void saveReservationData() {
        reservationDAO.saveToFile(reservationList);
    }

    private String generateReservationID() {
        return String.format("R%03d", reservationCount++);
    }

    private Book findBookById(String bookID) {
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book != null && book.getBookID() != null && book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }

    private Student findStudentById(String studentID) {
        for (int i = 1; i <= studentList.size(); i++) {
            Student student = studentList.get(i);
            if (student != null && student.getStudentID() != null && student.getStudentID().equalsIgnoreCase(studentID)) {
                return student;
            }
        }
        return null;
    }

    private Student buildTemporaryStudent(String studentID, String studentName) {
        Student student = new Student();
        student.setStudentID(studentID);
        student.setStudentName(studentName == null ? "" : studentName);
        return student;
    }

    public String findStudentNameFromBorrowRecord(String studentID) {
        reloadData();

        for (int i = 1; i <= borrowRecordList.size(); i++) {
            BorrowRecord record = borrowRecordList.get(i);

            if (record != null
                    && record.getBorrowerID() != null
                    && record.getBorrowerID().equalsIgnoreCase(studentID)
                    && record.getBorrowName() != null
                    && !record.getBorrowName().trim().isEmpty()) {
                return record.getBorrowName();
            }
        }

        return null;
    }

    private Reservation findReservation(String studentID, String bookID) {
        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getStudentID() != null
                    && r.getBookID() != null
                    && r.getStudentID().equalsIgnoreCase(studentID)
                    && r.getBookID().equalsIgnoreCase(bookID)
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
                    && r.getStudentID() != null
                    && r.getBookID() != null
                    && r.getStudentID().equalsIgnoreCase(studentID)
                    && r.getBookID().equalsIgnoreCase(bookID)
                    && !"Cancelled".equalsIgnoreCase(r.getStatus())
                    && !"Book Removed".equalsIgnoreCase(r.getStatus())
                    && !"Notified".equalsIgnoreCase(r.getStatus())) {
                return true;
            }
        }

        Book book = findBookById(bookID);
        if (book != null && book.getWaitingList() != null) {
            for (int i = 1; i <= book.getWaitingList().size(); i++) {
                Student s = book.getWaitingList().get(i);
                if (s != null && s.getStudentID() != null && s.getStudentID().equalsIgnoreCase(studentID)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean removeStudentFromWaitingList(Book book, String studentID) {
        ListInterface<Student> waitingList = book.getWaitingList();

        for (int i = 1; i <= waitingList.size(); i++) {
            Student s = waitingList.get(i);

            if (s != null && s.getStudentID() != null && s.getStudentID().equalsIgnoreCase(studentID)) {
                waitingList.remove(i);
                return true;
            }
        }

        return false;
    }

    private String getCurrentDate() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return today.toString();
    }

    public String reserveBook(String studentID, String bookID, String studentName) {
        reloadData();

        Book book = findBookById(bookID);
        if (book == null) {
            return "Reservation failed. This book does not exist.";
        }

        if (book.isIsAvailable()) {
            return "Reservation is not necessary. The book currently available to borrow.";
        }

        if (hasDuplicateReservation(studentID, bookID)) {
            return "Reservation failed. This student is reserved this book within waiting list.";
        }

        Student student = findStudentById(studentID);

        if (student == null) {
            String borrowedName = findStudentNameFromBorrowRecord(studentID);

            if (borrowedName != null && !borrowedName.trim().isEmpty()) {
                studentName = borrowedName;
            }

            if (studentName == null || studentName.trim().isEmpty()) {
                return "Reservation failed. Student name is required.";
            }

            student = buildTemporaryStudent(studentID, studentName);
        }

        book.getWaitingList().add(student);

        Reservation reservation = new Reservation(
                generateReservationID(),
                student.getStudentID(),
                student.getStudentName(),
                book.getBookID(),
                book.getTitle(),
                getCurrentDate(),
                "Active"
        );

        reservationList.add(reservation);

        bookDAO.saveToFile(bookList);
        saveReservationData();

        return "Reservation successful. " + student.getStudentName()
                + " has been added to the waiting list for \"" + book.getTitle()
                + "\".";
    }

    public String cancelReservation(String studentID, String bookID) {
        reloadData();

        Book book = findBookById(bookID);
        if (book == null) {
            return "Cancellation failed. Book does not exist.";
        }

        Reservation reservation = findReservation(studentID, bookID);
        boolean removed = removeStudentFromWaitingList(book, studentID);

        if (reservation == null && !removed) {
            return "Cancellation failed. Reservation record not found.";
        }

        if (reservation != null) {
            reservation.setStatus("Cancelled");
        }

        bookDAO.saveToFile(bookList);
        saveReservationData();

        String studentName = (reservation != null && reservation.getStudentName() != null)
                ? reservation.getStudentName()
                : studentID;

        return "Reservation cancelled successfully. \n" + studentName
                + " has been removed from the waiting list for \"" + book.getTitle()
                + "\".";
    }

    public String viewWaitingList(String bookID) {
        reloadData();

        Book book = findBookById(bookID);

        if (book == null) {
            return "View waiting list failed. Book does not exist.";
        }

        if (book.getWaitingList() == null || book.getWaitingList().isEmpty()) {
            return "Waiting list is empty for book \"" + book.getTitle() + "\".";
        }

        StringBuilder output = new StringBuilder();
        output.append("Waiting List for \"")
              .append(book.getTitle())
              .append("\" (")
              .append(book.getBookID())
              .append("):\n");

        for (int i = 1; i <= book.getWaitingList().size(); i++) {
            Student student = book.getWaitingList().get(i);
            output.append(i)
                  .append(". ")
                  .append(student.getStudentID())
                  .append(" - ")
                  .append(student.getStudentName())
                  .append("\n");
        }

        return output.toString();
    }

    public String notifyNextStudent(String bookID) {
        reloadData();

        Book book = findBookById(bookID);

        if (book == null) {
            return "Notification failed. Book does not exist.";
        }

        if (book.getWaitingList() == null || book.getWaitingList().isEmpty()) {
            return "No notification performed. Waiting list is empty, and admin is informed.";
        }

        Student nextStudent = book.getWaitingList().get(1);
        book.getWaitingList().remove(1);

        Reservation reservation = findReservation(nextStudent.getStudentID(), bookID);
        if (reservation != null) {
            reservation.setStatus("Notified");
        }

        bookDAO.saveToFile(bookList);
        saveReservationData();

        return "Notification sent to " + nextStudent.getStudentName()
                + " (" + nextStudent.getStudentID()
                + ") for book \"" + book.getTitle() + "\".";
    }

    public String notifyDelay(String bookID) {
        reloadData();

        Book book = findBookById(bookID);

        if (book == null) {
            return "Delay notification failed. Book does not exist.";
        }

        if (book.getWaitingList() == null || book.getWaitingList().isEmpty()) {
            return "No delay notification performed. Waiting list is empty.";
        }

        Student nextStudent = book.getWaitingList().get(1);

        Reservation reservation = findReservation(nextStudent.getStudentID(), bookID);
        if (reservation != null) {
            reservation.setStatus("Delayed");
        }

        saveReservationData();

        return "Delay notification sent to " + nextStudent.getStudentName()
                + " (" + nextStudent.getStudentID()
                + ") for book \"" + book.getTitle() + "\".";
    }

    public String notifyBookRemoval(String bookID) {
        reloadData();

        Book book = findBookById(bookID);

        if (book == null) {
            return "Book removal notification failed. Book does not exist.";
        }

        if (book.getWaitingList() == null || book.getWaitingList().isEmpty()) {
            return "No students to notify. Waiting list is empty for book \"" + book.getTitle() + "\".";
        }

        int count = book.getWaitingList().size();

        for (int i = 1; i <= count; i++) {
            Student student = book.getWaitingList().get(i);
            Reservation reservation = findReservation(student.getStudentID(), bookID);

            if (reservation != null) {
                reservation.setStatus("Book Removed");
            }
        }

        book.getWaitingList().clear();
        bookDAO.saveToFile(bookList);
        saveReservationData();

        return count + " student(s) notified that book \"" + book.getTitle() + "\" has been removed.";
    }

    public Reservation findNotifiedReservationByStudent(String studentID) {
        reloadData();

        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getStudentID() != null
                    && r.getStudentID().equalsIgnoreCase(studentID)
                    && "Notified".equalsIgnoreCase(r.getStatus())) {
                return r;
            }
        }
        return null;
    }

    public Reservation findFirstActiveReservationByBook(String bookID) {
        reloadData();

        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                    && r.getBookID() != null
                    && r.getBookID().equalsIgnoreCase(bookID)
                    && "Active".equalsIgnoreCase(r.getStatus())) {
                return r;
            }
        }
        return null;
    }

    public boolean markReservationAsEnded(String studentID, String bookID) {
        reloadData();

        Reservation reservation = findReservation(studentID, bookID);
        if (reservation == null) {
            return false;
        }

        reservation.setStatus("Cancelled");
        saveReservationData();
        return true;
    }

    public ListInterface<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ListInterface<Book> bookList) {
        this.bookList = bookList;
    }

    public ListInterface<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ListInterface<Student> studentList) {
        this.studentList = studentList;
    }

    public ListInterface<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ListInterface<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}