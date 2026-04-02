/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author user
 */
public class Borrower {
    private String borrowerId;
    
    private String role;
    
    public Borrower(String borrowerId, String role) {
        this.borrowerId = borrowerId;
        this.role = role;
    }

    // getters
    public String getBorrowerID() {
        return borrowerId;
    }

  

    public String getBorrowerType() {
        return role;
    }

    // setters
  

    public void setBorrowerType(String role) {
        this.role = role;
    }

    // toString
    @Override
    public String toString() {
        return "ID: " + borrowerId +
               ", Role: " + role;
    }
    
}
