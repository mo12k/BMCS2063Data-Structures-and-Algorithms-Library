/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author Yang
 */
public class FineCalculator {
    private static final double RATE_PER_DAY = 0.50;

    public static double calculateFine(int overdueDays) {
        return overdueDays * RATE_PER_DAY;
    }
}