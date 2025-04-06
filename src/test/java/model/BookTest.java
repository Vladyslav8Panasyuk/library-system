package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.model.Book;

class BookTest {
    
    @Test
    void testBooksAreEqual() {
        Book firstBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        Book secondBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997, 
            309);

        Assertions.assertEquals(firstBook, secondBook);
    }

    @Test
    void testBooksAreNotEqual() {
        Book firstBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        Book secondBook = new Book(
            "Harry Potter and the Prisoner of Azkaban", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1999,
            435);

        Assertions.assertNotEquals(firstBook, secondBook);
    }
}
