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
import entity.Book;
import entity.Student;

public class testReserve {
    public static void main(String[] args) {
        control.BookReservation control = new control.BookReservation();
        boundary.BookReservationUI ui = new boundary.BookReservationUI(control);
        ui.start();
    }
}

