/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;

/**
 * A doubly-linked list implementation of ListInterface.
 * This class uses bidirectional links to traverse the list efficiently.
 *
 * @author Mok
 * @param <T> the type of entries in the list
 */
public class DoublyLinkedList<T> implements ListInterface<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> implements Serializable {
        private static final long serialVersionUID = 1L;

        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Creates an empty doubly-linked list.
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Task: Adds a new entry to the end of the list. Entries currently in the
     * list are unaffected. The list's size is increased by 1.
     *
     * @param newEntry the object to be added as a new entry
     * @return true if the addition is successful, or false if the list is full
     */
    @Override
    public boolean add(T newEntry) {
        Node<T> newNode = new Node<>(newEntry);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
        return true;
    }

    /**
     * Task: Adds a new entry at a specified position within the list. Entries
     * originally at and above the specified position are at the next higher
     * position within the list. The list's size is increased by 1.
     *
     * @param index an integer that specifies the desired position of the new entry
     * @param newEntry the object to be added as a new entry
     * @return true if the addition is successful, or false if either the list is full,
     *         index < 1, or index > size() + 1
     */
    @Override
    public boolean add(int index, T newEntry) {
        if (index < 1 || index > size + 1) {
            return false;
        }

        if (index == size + 1) {
            return add(newEntry);
        }

        Node<T> newNode = new Node<>(newEntry);
        Node<T> current = getNode(index);

        newNode.next = current;
        newNode.prev = current.prev;

        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            head = newNode;
        }

        current.prev = newNode;
        size++;
        return true;
    }

    /**
     * Task: Retrieves the entry at a given position in the list.
     *
     * @param index an integer that indicates the position of the desired entry
     * @return a reference to the indicated entry or null, if either the list is
     *         empty, index < 1, or index > size()
     */
    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node == null ? null : node.data;
    }

    /**
     * Task: Replaces the entry at a given position in the list.
     *
     * @param index an integer that indicates the position of the entry to be replaced
     * @param anEntry the object that will replace the entry at the position index
     * @return the previous entry at the specified position, or null if index is invalid
     */
    @Override
    public T set(int index, T anEntry) {
        Node<T> node = getNode(index);
        if (node == null) {
            return null;
        }

        T oldValue = node.data;
        node.data = anEntry;
        return oldValue;
    }

    /**
     * Task: Removes the entry at a given position from the list. Entries
     * originally at positions higher than the given position are at the next
     * lower position within the list, and the list's size is decreased by 1.
     *
     * @param index an integer that indicates the position of the entry to be removed
     * @return a reference to the removed entry or null, if either the list was empty,
     *         index < 1, or index > size()
     */
    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (node == null) {
            return null;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        size--;
        return node.data;
    }

    /**
     * Task: Removes the first occurrence of a specific entry from the list.
     *
     * @param anEntry the object to be removed from the list
     * @return true if the removal is successful, or false if the list does not contain anEntry
     */
    @Override
    public boolean remove(T anEntry) {
        int position = indexOf(anEntry);
        if (position == -1) {
            return false;
        }

        remove(position);
        return true;
    }

    /**
     * Task: Sees whether the list contains a given entry.
     *
     * @param anEntry the object that is the desired entry
     * @return true if the list contains anEntry, or false if not
     */
    @Override
    public boolean contains(T anEntry) {
        return indexOf(anEntry) != -1;
    }

    /**
     * Task: Gets the position of an entry in the list.
     *
     * @param anEntry the object to locate in the list
     * @return the position (1-based) of the first occurrence, or -1 if not found
     */
    @Override
    public int indexOf(T anEntry) {
        Node<T> current = head;
        int index = 1;

        while (current != null) {
            if (anEntry == null ? current.data == null : anEntry.equals(current.data)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    /**
     * Task: Gets the number of entries in the list.
     *
     * @return the integer number of entries currently in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Task: Sees whether the list is empty.
     *
     * @return true if the list is empty, or false if not
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Task: Removes all entries from the list.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Replaces the entry at a given position in the list. Convenience method that
     * delegates to set().
     *
     * @param givenPosition an integer that indicates the position of the entry to be replaced
     * @param newEntry the object that will replace the entry at the position
     * @return true if the replacement occurs, or false if either the list is empty,
     *         givenPosition < 1, or givenPosition > size()
     */
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 1 || givenPosition > size) {
            return false;
        }

        set(givenPosition, newEntry);
        return true;
    }

    /**
     * Retrieves the entry at a given position in the list. Convenience method that
     * delegates to get().
     *
     * @param givenPosition an integer that indicates the position of the desired entry
     * @return a reference to the indicated entry or null, if either the list is empty,
     *         givenPosition < 1, or givenPosition > size()
     */
    public T getEntry(int givenPosition) {
        return get(givenPosition);
    }

    /**
     * Gets the number of entries in the list. Convenience method that delegates to size().
     *
     * @return the integer number of entries currently in the list
     */
    public int getNumberOfEntries() {
        return size();
    }

    /**
     * Sees whether the list is full.
     *
     * @return true if the list is full, or false if not (always false for dynamic lists)
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Private helper method to find and return the node at a given position.
     * Uses bidirectional traversal for efficiency.
     *
     * @param index the position of the node to retrieve (1-based)
     * @return the node at the given position, or null if index is invalid
     */
    private Node<T> getNode(int index) {
        if (index < 1 || index > size) {
            return null;
        }

        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    /**
     * Returns a string representation of the list with each entry on a new line.
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        String outputStr = "";
        Node<T> current = head;
        while (current != null) {
            outputStr += current.data + "\n";
            current = current.next;
        }
        return outputStr;
    }
}
