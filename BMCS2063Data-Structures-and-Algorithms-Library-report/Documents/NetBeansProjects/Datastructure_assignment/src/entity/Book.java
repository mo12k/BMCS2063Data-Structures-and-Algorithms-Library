package entity;

import adt.DoublyLinkedList;
import adt.ListInterface;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookID;
    private String title;
    private String author;
    private String category;
    private int yearPublished;
    private int quantity;
    private boolean isAvailable;
    private ListInterface<Student> waitingList;

    private static int BookCount = 1;

    public Book() {
        this.waitingList = new DoublyLinkedList<>();
    }

    public Book(String title, String author, String category, int yearPublished, boolean isAvailable) {
        this.bookID = generateBookID();
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        this.quantity = 1;
        this.isAvailable = isAvailable;
        this.waitingList = new DoublyLinkedList<>();
    }

    public Book(String title, String author, String category, int yearPublished, int quantity) {
        this.bookID = generateBookID();
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        setQuantity(quantity);
        this.isAvailable = this.quantity > 0;
        this.waitingList = new DoublyLinkedList<>();
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
        this.isAvailable = this.quantity > 0;
    }

    public boolean isIsAvailable() {
        return isAvailable && quantity > 0;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public ListInterface<Student> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(ListInterface<Student> waitingList) {
        this.waitingList = waitingList;
    }

    public int getWaitingListCount() {
                if (waitingList == null) {
                return 0;
            }
            return waitingList.size();
    }

    private String generateBookID() {
        return String.format("B%04d", BookCount++);
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
        return String.format("%s | %s | %s | %s | %d | Qty: %d | Available: %s | Waiting List: %d",
                bookID,
                title,
                author,
                category,
                yearPublished,
                quantity,
                (isIsAvailable() ? "Yes" : "No"),
                getWaitingListCount());
    }
}