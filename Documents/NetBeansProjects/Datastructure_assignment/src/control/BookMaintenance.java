/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import boundary.BookMaintenanceUI;
import entity.*;
import dao.BookDAO;
import utility.MessageUI;

/**
 *
 * @author Mok
 */
public class BookMaintenance {
    private ListInterface<Book> bookList = new ArrayList<>();
    private BookDAO bookDAO = new BookDAO();
    private BookMaintenanceUI bookUI = new BookMaintenanceUI();
    
    public BookMaintenance(){
        bookList = bookDAO.retriveFromFile();
    }
    
    public void startRunLibrary(){
        int mainChoice = 0;
        do{
            mainChoice = bookUI.getMainMenuChoice();
            switch(mainChoice){
                case 0 -> {
                    MessageUI.displayExitMessage();
                    break;
                } 
                case 1:
                    runStaffMenu();
                    break;
                case 2:
                    runStudentMenu();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        }while(mainChoice !=0);
    }
    
    public void runStaffMenu(){
        int choice =0;
        do{
            choice = bookUI.getStaffMenu();
            switch(choice){
                case 1:
                    addNewBook();
                    bookUI.listAllBooks(getAllProducts());
                    break;
                case 2:
                    System.out.println("Enter Book (ID/Title/Author) to search:");
                    String searchName = bookUI.getSearchInput();
                    searchBook(searchName);
                    
            }
        }
    }
    public void runStudentMenu(){
        int choice = 0;
        do{
            choice = bookUI
        }
    }
    
    public ArrayList<Book> searchBook (String searchName){
        ArrayList<Book> matchingBooks = new ArrayList<>();
        String searchLower = searchName.toLowerCase().trim();
        
        for(Book books : bookList){
            if(books.getAuthor().toLowerCase().contains(searchLower) || books.getBookID().toLowerCase().contains(searchLower) && books.getTitle().toLowerCase().contains(searchLower)){
                matchingBooks.add(books);
            }
        }
        return matchingBooks;
    }
    
    public void addNewBook(){
        Book newBook = bookUI.inputBookDetails();
        bookList.add(newBook);
        bookDAO.saveToFile(bookList);
    }
    
    public String getAllProducts(){
        String outputStr ="";
        for(int i = 0; i<=bookList.getNumberOfEntries();i++){
            outputStr += bookList.getEntry(i)+ "\n";
        }
        return outputStr;
    }
    
    public void displayBooks(){
        bookUI.listAllBooks(getAllProducts());
    }
    
    public static void main(String[] args){
        BookMaintenance bookMaintenance = new BookMaintenance();
        bookMaintenance.startRunLibrary();
    }
}