/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author user
 */
import java.util.Scanner;

public class UITools {
    public static Scanner scanner = new Scanner(System.in);
    
    public static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static void pressEnterToContinue() {
        System.out.println("--------------------------------------------------");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
