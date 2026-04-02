/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author Mok
 * @param <T>
 */
public interface ListInterface<T> {
    boolean add(T newEntry);
    
    boolean add(int index, T newEntry);
    
    T get(int index);
    
    T set(int index, T anEntry);
    
    T remove(int index);
    
    boolean remove(T anEntry);
    
    boolean contains(T anEntry);
    
    int indexOf(T anEntry);
    
    int size();
    
    boolean isEmpty();
    
    void clear();
}
