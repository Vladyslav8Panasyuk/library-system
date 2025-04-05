package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.model.Book;

class BookTest {
    
    @Test
    void testBorrow() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            2001);

        book.borrow();

        Assertions.assertTrue(book.isBorrowed());
    }

    @Test
    void testBorrowException() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            2001);

        book.borrow();

        Assertions.assertThrows(IllegalStateException.class, () -> book.borrow());
    }

    @Test
    void testGiveBack() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            2001);

        book.borrow();
        book.giveBack();

        Assertions.assertFalse(book.isBorrowed());
    }

    @Test
    void testGiveBackException() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            2001);

        Assertions.assertThrows(IllegalStateException.class, () -> book.giveBack());
    }

    @Test
    void testBooksAreEqual() {
        Book firstBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997);

        Book secondBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997);

        Assertions.assertEquals(firstBook, secondBook);
    }

    @Test
    void testBooksAreNotEqual() {
        Book firstBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997);

        Book secondBook = new Book(
            "Harry Potter and the Prisoner of Azkaban", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1999);

        Assertions.assertNotEquals(firstBook, secondBook);
    }
}
