/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class testpush {
     public void startRunLibrary(){
        int mainChoice = 0;
        do{
            mainChoice = bookUI.getMainMenuChoice();
            switch(mainChoice){
                case 0 -> {
                    MessageUI.displayExitMessage();
                    break;
                } 
                case 1 -> {
                    runStaffMenu();
                    break;
                }
                case 2 -> {
                    runStudentMenu();
                    break;
                }
                default-> {
                    MessageUI.displayInvalidChoiceMessage();
                    break;
                }
            }
        }while(mainChoice !=0);
}
