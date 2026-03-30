/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author Yang
 */

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static int calculateOverdueDays(LocalDate dueDate) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            return (int) ChronoUnit.DAYS.between(dueDate, today);
        }
        return 0;
    }
}
