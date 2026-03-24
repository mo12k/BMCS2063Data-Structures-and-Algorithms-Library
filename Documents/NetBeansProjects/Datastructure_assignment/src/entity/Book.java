/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Mok
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookID;
    private String title;
    private String author;
    private String category;
    private int yearPublished;
    private int quantity;
    private boolean isAvailable;
    
    private static int BookCount = 1;

    public Book() {
    }

    public Book(String title, String author, String category, int yearPublished, boolean isAvailable) {
        this.bookID = generateBookID();
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        this.quantity = 1;
        this.isAvailable = isAvailable;
    }

    public Book(String title, String author, String category, int yearPublished, int quantity) {
        this.bookID = generateBookID();
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        setQuantity(quantity);
        this.isAvailable = this.quantity > 0;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public boolean isIsAvailable() {
        return isAvailable && quantity > 0;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    private String generateBookID(){
        String id = String.format("B%04d", BookCount++);
        return id;
}

    public static void setNextBookNumber(int nextBookNumber) {
        if (nextBookNumber < 1) {
            BookCount = 1;
        } else {
            BookCount = nextBookNumber;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.bookID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Book other = (Book) obj;
        return Objects.equals(this.bookID, other.bookID);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %d | Qty: %d | Available: %s",
                bookID,
                title,
                author,
                category,
                yearPublished,
                quantity,
                (isIsAvailable() ? "Yes" : "No"));
    }
}
