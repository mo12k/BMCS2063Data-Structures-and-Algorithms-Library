# ADT Specification: List Interface

## 1. Introduction

The **ListInterface** is a generic Abstract Data Type (ADT) that defines a contract for ordered collection data structures. It supports fundamental operations for managing a collection of elements of any type `<T>`. This specification defines the interface for two implementations: **ArrayList** and **DoublyLinkedList**.

---

## 2. ADT Definition

### Name
`ListInterface<T>` - A generic list abstract data type

### Purpose
To provide a flexible, type-safe container for managing ordered collections of elements with operations for insertion, deletion, retrieval, searching, and filtering.

### Type Parameter
- `<T>` - Generic type representing the type of elements stored in the list

---

## 3. Data Structure Properties

- **Ordered**: Elements maintain insertion order; accessible by position (1-based indexing)
- **Dynamic**: Grows and shrinks as elements are added or removed
- **Generic**: Type-safe through Java generics; works with any data type
- **Homogeneous**: All elements must be of the same type `<T>`

---

## 4. Operations Specification

### 4.1 Add Operations

#### `boolean add(T newEntry)`
**Purpose**: Add an element to the end of the list

**Parameters**:
- `newEntry`: The element to add (type `T`)

**Return Value**:
- `true` if addition is successful

**Precondition**: None (list must exist)

**Postcondition**: Element is added at the end; size increases by 1

**Time Complexity**: 
- ArrayList: O(1) amortized
- DoublyLinkedList: O(1)

---

#### `boolean add(int newPosition, T newEntry)`
**Purpose**: Add an element at a specific position

**Parameters**:
- `newPosition`: The position where element should be inserted (1-based indexing)
- `newEntry`: The element to add (type `T`)

**Return Value**:
- `true` if successful; `false` if position is invalid

**Precondition**: `1 <= newPosition <= size + 1`

**Postcondition**: Element inserted at specified position; subsequent elements shift; size increases by 1

**Time Complexity**:
- ArrayList: O(n) due to shifting
- DoublyLinkedList: O(n) in worst case for traversal

---

### 4.2 Remove Operations

#### `T remove(int givenPosition)`
**Purpose**: Remove and return element at specified position

**Parameters**:
- `givenPosition`: Position of element to remove (1-based indexing)

**Return Value**:
- The element removed, or `null` if position is invalid

**Precondition**: `1 <= givenPosition <= size`

**Postcondition**: Element at position removed; subsequent elements shift; size decreases by 1

**Time Complexity**:
- ArrayList: O(n) due to shifting
- DoublyLinkedList: O(n) in worst case for traversal

---

### 4.3 Replacement Operation

#### `boolean replace(int givenPosition, T newEntry)`
**Purpose**: Replace element at specified position without changing size

**Parameters**:
- `givenPosition`: Position of element to replace (1-based indexing)
- `newEntry`: The new element (type `T`)

**Return Value**:
- `true` if replacement successful; `false` if position is invalid

**Precondition**: `1 <= givenPosition <= size`

**Postcondition**: Element at position replaced; size unchanged

**Time Complexity**:
- ArrayList: O(1)
- DoublyLinkedList: O(n) for traversal

---

### 4.4 Retrieval Operations

#### `T getEntry(int givenPosition)`
**Purpose**: Retrieve element at specified position without removing it

**Parameters**:
- `givenPosition`: Position of element to retrieve (1-based indexing)

**Return Value**:
- Element at position, or `null` if position is invalid

**Precondition**: `1 <= givenPosition <= size`

**Postcondition**: List unchanged

**Time Complexity**:
- ArrayList: O(1)
- DoublyLinkedList: O(n) but optimized for head/tail access

---

### 4.5 Search Operations

#### `boolean contains(T anEntry)`
**Purpose**: Check if list contains a specific element

**Parameters**:
- `anEntry`: Element to search for (type `T`)

**Return Value**:
- `true` if element found; `false` otherwise

**Precondition**: None

**Postcondition**: List unchanged

**Time Complexity**: O(n) - linear search

---

#### `ListInterface<T> findAll(java.util.function.Predicate<T> predicate)`
**Purpose**: Find all elements matching a condition

**Parameters**:
- `predicate`: A condition (Predicate) that elements must satisfy

**Return Value**:
- New ListInterface containing all matching elements

**Precondition**: None

**Postcondition**: Original list unchanged; new list created with results

**Time Complexity**: O(n) - must examine all elements

**Example Usage**:
```java
ListInterface<Book> availableBooks = bookList.findAll(book -> book.isAvailable());
ListInterface<Book> booksByAuthor = bookList.findAll(book -> book.getAuthor().equals("Author Name"));
```

---

#### `T find(java.util.function.Predicate<T> predicate)`
**Purpose**: Find first element matching a condition

**Parameters**:
- `predicate`: A condition (Predicate) that element must satisfy

**Return Value**:
- First matching element, or `null` if no match found

**Precondition**: None

**Postcondition**: List unchanged; search terminates at first match

**Time Complexity**: O(n) worst case - stops at first match

**Example Usage**:
```java
Book firstAvailableBook = bookList.find(book -> book.isAvailable());
Book specificBook = bookList.find(book -> book.getBookID().equals("B001"));
```

---

### 4.6 Utility Operations

#### `void clear()`
**Purpose**: Remove all elements

**Return Value**: void

**Postcondition**: List is empty; size = 0

**Time Complexity**: 
- ArrayList: O(1)
- DoublyLinkedList: O(1)

---

#### `int getNumberOfEntries()`
**Purpose**: Get current number of elements

**Return Value**: Number of elements (size)

**Precondition**: None

**Postcondition**: List unchanged

**Time Complexity**: O(1)

---

#### `boolean isEmpty()`
**Purpose**: Check if list is empty

**Return Value**:
- `true` if size = 0; `false` otherwise

**Precondition**: None

**Postcondition**: List unchanged

**Time Complexity**: O(1)

---

#### `boolean isFull()`
**Purpose**: Check if list is full (capacity reached)

**Return Value**:
- ArrayList: `true` if array at capacity (but can resize)
- DoublyLinkedList: `false` (linked lists are never full)

**Precondition**: None

**Postcondition**: List unchanged

**Time Complexity**: O(1)

---

## 5. Implementations

### 5.1 ArrayList Implementation
- **Structure**: Dynamic array with automatic resizing
- **Indexing**: 1-based (converted to 0-based internally)
- **Advantage**: Fast random access O(1)
- **Disadvantage**: Slow insertion/deletion at middle O(n)

### 5.2 DoublyLinkedList Implementation
- **Structure**: Doubly-linked nodes (each node has prev/next pointers)
- **Indexing**: 1-based (traversed from head or tail)
- **Advantage**: Efficient insertion/deletion O(1) if position known; bidirectional traversal
- **Disadvantage**: Slow random access O(n); extra memory for pointers

---

## 6. Generic Type Usage

All operations are generic using type parameter `<T>`:
- Type-safe at compile time
- No casting required
- Works with any object type (Book, Student, etc.)
- Does not work with primitives (use wrapper classes: Integer, Double, etc.)

### Example:
```java
ListInterface<Book> bookList = new DoublyLinkedList<>();
ListInterface<String> titleList = new ArrayList<>();
ListInterface<Integer> numberList = new DoublyLinkedList<>();
```

---

## 7. Exception Handling

The ADT specification does NOT throw exceptions. Instead:
- Invalid operations return `false` or `null` (depending on operation)
- Callers must check return values before using results

**Examples**:
```java
if (bookList.add(-1, newBook) == false) {
    System.out.println("Invalid position");
}

Book found = bookList.find(book -> book.getBookID().equals("B001"));
if (found == null) {
    System.out.println("Book not found");
}
```

---

## 8. Key Design Principles

1. **Genericity**: Generic type parameter for flexibility
2. **1-Based Indexing**: User-friendly indexing (position 1 is first element)
3. **Non-Exceptional Returns**: Use null/false instead of throwing exceptions
4. **Functional Programming**: Support Predicates for flexible searching/filtering
5. **Immutability of Results**: Search operations return new lists, not views

---

## 9. Class Diagrams

### ListInterface<T> (Abstract Data Type)
```
<<interface>>
ListInterface<T>
─────────────────────────────────────
+ add(T): boolean
+ add(int, T): boolean
+ remove(int): T
+ replace(int, T): boolean
+ getEntry(int): T
+ contains(T): boolean
+ find(Predicate<T>): T
+ findAll(Predicate<T>): ListInterface<T>
+ clear(): void
+ getNumberOfEntries(): int
+ isEmpty(): boolean
+ isFull(): boolean
```

### Implementations
```
DoublyLinkedList<T>     ArrayList<T>
     ↑                      ↑
     │                      │
  implements            implements
     │                      │
     └──────────┬───────────┘
              │
        ListInterface<T>
```

---

## 10. Usage Examples

### Adding Books
```java
ListInterface<Book> bookList = new DoublyLinkedList<>();
bookList.add(new Book("Java Programming", "John Doe", 2023));
bookList.add(1, new Book("Python Basics", "Jane Smith", 2022));
```

### Searching Books (Creative ADT Use)
```java
// Find first available book
Book available = bookList.find(book -> book.isAvailable());

// Find all books by specific author
ListInterface<Book> booksByAuthor = bookList.findAll(
    book -> book.getAuthor().equals("John Doe")
);

// Find all books published after 2020
ListInterface<Book> recentBooks = bookList.findAll(
    book -> book.getYearPublished() > 2020
);
```

### Updating and Removing
```java
// Replace book at position 2
bookList.replace(2, updatedBook);

// Remove book at position 1
Book removed = bookList.remove(1);
```

---

## 11. Performance Comparison

| Operation | ArrayList | DoublyLinkedList |
|-----------|-----------|-----------------|
| add() tail | O(1) | O(1) |
| add(pos) | O(n) | O(n) |
| remove(pos) | O(n) | O(n) |
| getEntry(pos) | O(1) | O(n) |
| contains() | O(n) | O(n) |
| findAll() | O(n) | O(n) |

---

## 12. Conclusion

The **ListInterface** ADT provides a flexible, type-safe abstraction for ordered collections. Implementations support both efficient random access (ArrayList) and efficient structural modifications (DoublyLinkedList). The inclusion of functional search operations (find, findAll) enables creative, high-level programming patterns while maintaining efficient implementations.

