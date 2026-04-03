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
import entity.Book;
import entity.Student;
import entity.Reservation;

public class BookReservation {

    private ListInterface<Book> bookList;
    private ListInterface<Student> studentList;
    private ListInterface<Reservation> reservationList;
    private static int reservationCount = 1;

    public BookReservation() {
        bookList = new DoublyLinkedList<>();
        studentList = new DoublyLinkedList<>();
        reservationList = new DoublyLinkedList<>();

        preloadData();
    }

    private String generateReservationID() {
        return String.format("R%03d", reservationCount++);
    }

    private Book findBookById(String bookID) {
        for (int i = 1; i <= bookList.size(); i++) {
            Book book = bookList.get(i);
            if (book != null && book.getBookID().equalsIgnoreCase(bookID)) {
                return book;
            }
        }
        return null;
    }

    private Student findStudentById(String studentID) {
        for (int i = 1; i <= studentList.size(); i++) {
            Student student = studentList.get(i);
            if (student != null && student.getStudentID().equalsIgnoreCase(studentID)) {
                return student;
            }
        }
        return null;
    }

    private Reservation findReservation(String studentID, String bookID) {
        for (int i = 1; i <= reservationList.size(); i++) {
            Reservation r = reservationList.get(i);

            if (r != null
                && r.getStudent() != null
                && r.getBook() != null
                && r.getStudent().getStudentID().equalsIgnoreCase(studentID)
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
                && r.getStudent() != null
                && r.getBook() != null
                && r.getStudent().getStudentID().equalsIgnoreCase(studentID)
                && r.getBook().getBookID().equalsIgnoreCase(bookID)
                && !"Cancelled".equalsIgnoreCase(r.getStatus())
                && !"Book Removed".equalsIgnoreCase(r.getStatus())
                && !"Notified".equalsIgnoreCase(r.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private boolean removeStudentFromWaitingList(Book book, String studentID) {
        ListInterface<Student> waitingList = book.getWaitingList();

        for (int i = 1; i <= waitingList.size(); i++) {
            Student s = waitingList.get(i);

            if (s != null && s.getStudentID().equalsIgnoreCase(studentID)) {
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
    
    public String reserveBook(String studentID, String bookID) {
        Student student = findStudentById(studentID);
        if (student == null) {
            return "Reservation failed. This student does not exist.";
        }

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

        book.getWaitingList().add(student);

        Reservation reservation = new Reservation(generateReservationID(), book, student, getCurrentDate(), "Active");

        reservationList.add(reservation);

        return "Reservation successful. " + student.getStudentName() + 
               " has been added to the waiting list for \"" + book.getTitle() + 
               "\".";}

    public String cancelReservation(String studentID, String bookID) {
        Student student = findStudentById(studentID);
        if (student == null) {
            return "Cancellation failed. Student does not exist.";
        }

        Book book = findBookById(bookID);
        if (book == null) {
            return "Cancellation failed. Book does not exist.";
        }

        Reservation reservation = findReservation(studentID, bookID);
        if (reservation == null) {
            return "Cancellation failed. Reservation record not found.";
        }

        boolean removed = removeStudentFromWaitingList(book, studentID);
        if (!removed) {
            return "Cancellation failed. Student is not in the waiting list.";
        }

        reservation.setStatus("Cancelled");

        return "Reservation cancelled successfully. \n" + student.getStudentName() +
               " has been removed from the waiting list for \"" + book.getTitle() + 
               "\".";
    }

    public String viewWaitingList(String bookID) {
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

        return "Notification sent to " + nextStudent.getStudentName() + 
               " (" + nextStudent.getStudentID() + 
               ") for book \"" + book.getTitle() + "\".";
    }

    public String notifyDelay(String bookID) {
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

        return "Delay notification sent to " + nextStudent.getStudentName() + 
               " (" + nextStudent.getStudentID() + 
               ") for book \"" + book.getTitle() + "\".";
    }

    public String notifyBookRemoval(String bookID) {
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

        return count + " student(s) notified that book \"" + book.getTitle() + "\" has been removed.";
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

    public void preloadData(){
        studentList.add(new Student("S001", "Alice"));
        studentList.add(new Student("S002", "Bob"));
        studentList.add(new Student("S003", "Charlie"));

        Book b1 = new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", 2018, 0);
        Book b2 = new Book("B002", "To Kill a Mockingbird", "Harper Lee", 2015, 0);
        Book b3 = new Book("B003", "1984", "George Orwell", 2016, 3);
        
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);
    }
}

