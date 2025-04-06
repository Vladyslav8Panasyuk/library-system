package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.model.Book;

class BookTest {
    
    @Test
    void testNextPage() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        int targetPageNumber = 10;
        for (int i = 1; i < targetPageNumber; i++) {
            book.nextPage();
        }

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @Test
    void testNextPageException() {
        Book book = new Book(
            "Test title", 
            "Test author", 
            "Test publisher", 
            2000, 
            2);

        book.nextPage();

        Assertions.assertThrows(IllegalStateException.class, () -> book.nextPage());
    }

    @Test
    void testPreviousPage() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        int upperPageNumber = 10;
        for (int i = 1; i < upperPageNumber; i++) {
            book.nextPage();
        }

        int targetPageNumber = 5;
        for (int i = 0; i < upperPageNumber - targetPageNumber; i++) {
            book.previousPage();
        }

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @Test
    void testPreviousPageException() {
        Book book = new Book(
            "Test title", 
            "Test author", 
            "Test publisher", 
            2000, 
            2);

        Assertions.assertThrows(IllegalStateException.class, () -> book.previousPage());
    }

    @Test
    void testGoToPage() {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        int targetPageNumber = 10;
        book.goToPage(targetPageNumber);

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 310})
    void testGoToPageException(int pageNumber) {
        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        Assertions.assertThrows(IllegalArgumentException.class, () -> book.goToPage(pageNumber));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, 0, 2026})
    void testSetPublicationYearException(int publicationYear) {
        Book book = new Book(
            "Test title", 
            "Test author", 
            "Test publisher", 
            2000, 
            2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> book.setPublicationYear(publicationYear));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, 0})
    void testSetPageCountException(int pageCount) {
        Book book = new Book(
            "Test title", 
            "Test author", 
            "Test publisher", 
            2000, 
            2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> book.setPageCount(pageCount));
    }

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
