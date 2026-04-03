///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
//package boundary;
//
///**
// *
// * @author lamzh
// */
//
//import control.BookReservation;
//import entity.Book;
//import entity.Student;
//
//public class testReserve {
//    public static void main(String[] args) {
//        BookReservation reservationControl = new BookReservation();
//
//        // Manually add books
//        Book b1 = new Book("Data Structures", "Mark Allen Weiss", "Computer Science", 2014, 0);
//        Book b2 = new Book("Java Programming", "Daniel Liang", "Programming", 2020, 2);
//        Book b3 = new Book("Database Systems", "Thomas Connolly", "Database", 2018, 0);
//        Book b4 = new Book("Computer Networks", "Andrew Tanenbaum", "Networking", 2019, 1);
//
//        reservationControl.getBookList().add(b1);
//        reservationControl.getBookList().add(b2);
//        reservationControl.getBookList().add(b3);
//        reservationControl.getBookList().add(b4);
//
//        // Manually add students
//        Student s1 = new Student("ST001", "Alice");
//        Student s2 = new Student("ST002", "Ben");
//        Student s3 = new Student("ST003", "Chloe");
//        Student s4 = new Student("ST004", "Daniel");
//        Student s5 = new Student("ST005", "Emily");
//
//        reservationControl.getStudentList().add(s1);
//        reservationControl.getStudentList().add(s2);
//        reservationControl.getStudentList().add(s3);
//        reservationControl.getStudentList().add(s4);
//        reservationControl.getStudentList().add(s5);
//
//        // Optional preloaded reservations for testing waiting list flow
//        //jia name
//        reservationControl.reserveBook("ST001", b1.getBookID(),s1.getStudentName());
//        reservationControl.reserveBook("ST002", b1.getBookID(),s1.getStudentName());
//        reservationControl.reserveBook("ST003", b3.getBookID(),s1.getStudentName());
//
//        // Launch UI
//        BookReservationUI ui = new BookReservationUI(reservationControl);
//        ui.startReservationModule();
//    }
//}
//
