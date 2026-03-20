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
    private String bookID;
    private String title;
    private String author;
    private String category;
    private int yearPublished;
    private boolean isAvailable;

    public Book() {
    }

    public Book(String bookID, String title, String author, String category, int yearPublished, boolean isAvailable) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        this.isAvailable = isAvailable;
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

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.bookID);
        hash = 29 * hash + Objects.hashCode(this.title);
        hash = 29 * hash + Objects.hashCode(this.author);
        hash = 29 * hash + Objects.hashCode(this.category);
        hash = 29 * hash + this.yearPublished;
        hash = 29 * hash + (this.isAvailable ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.yearPublished != other.yearPublished) {
            return false;
        }
        if (this.isAvailable != other.isAvailable) {
            return false;
        }
        if (!Objects.equals(this.bookID, other.bookID)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        return Objects.equals(this.category, other.category);
    }

    @Override
    public String toString() {
        return "Book{" + "bookID=" + bookID + ", title=" + title + ", author=" + author + ", category=" + category + ", yearPublished=" + yearPublished + ", isAvailable=" + isAvailable + '}';
    }
}
