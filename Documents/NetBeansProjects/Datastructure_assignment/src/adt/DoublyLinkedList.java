/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;

/**
 *
 * @author Mok
 */
public class DoublyLinkedList<T> implements DoublyLinkedListInterface<T>, ListInterface<T>, Serializable {

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

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

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

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node == null ? null : node.data;
    }

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

    @Override
    public boolean remove(T anEntry) {
        int position = indexOf(anEntry);
        if (position == -1) {
            return false;
        }

        remove(position);
        return true;
    }

    @Override
    public boolean contains(T anEntry) {
        return indexOf(anEntry) != -1;
    }

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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 1 || givenPosition > size) {
            return false;
        }

        set(givenPosition, newEntry);
        return true;
    }

    @Override
    public T getEntry(int givenPosition) {
        return get(givenPosition);
    }

    @Override
    public int getNumberOfEntries() {
        return size();
    }

    @Override
    public boolean isFull() {
        return false;
    }

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
