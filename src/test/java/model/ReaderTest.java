package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.model.Book;
import com.example.model.Reader;

class ReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"exampe123@example.com", "exampe_exampe@example.example"})
    void testValidSetEmail(String email) {
        Reader reader = new Reader();

        Assertions.assertDoesNotThrow(() -> reader.setEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"exampe123example.com", "exampe123@examplecom", "exampe 123@example.com"})
    void testInvalidSetEmail(String email) {
        Reader reader = new Reader();

        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.setEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+1234567890", "(888) 888-8888"})
    void testValidSetPhoneNumber(String phoneNumber) {
        Reader reader = new Reader();

        Assertions.assertDoesNotThrow(() -> reader.setPhoneNumber(phoneNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+12345", "888888888888888888888888", "abcdefgh1232"})
    void testInvalidSetPhoneNumber(String phoneNumber) {
        Reader reader = new Reader();

        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.setPhoneNumber(phoneNumber));
    }

    @Test
    void testReadBook() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int targetPageNumber = 101;
        int pageCount = 100;
        reader.addBook(book);
        reader.readBook(book, pageCount);

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @Test
    void testReadMissingBookException() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int pageCount = 100;
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.readBook(book, pageCount));
    }

    @Test
    void testReadBookPageCountException() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int pageCount = 400;
        reader.addBook(book);

        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.readBook(book, pageCount));
    }
    
    @Test
    void testReadPreviousBookPages() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int targetPageNumber = 60;
        int pageCount = 100;
        reader.addBook(book);
        reader.readBook(book, pageCount);
        reader.readPreviousBookPages(book, pageCount - targetPageNumber + 1);

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @Test
    void testReadPreviousBookPagesPageCountException() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int pageCount = 1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.readPreviousBookPages(book, pageCount));
    }

    @Test
    void testSkipBookPages() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int targetPageNumber = 101;
        int pageCount = 100;
        reader.addBook(book);
        reader.skipBookPages(book, pageCount);

        Assertions.assertEquals(book.getCurrentPageNumber(), targetPageNumber);
    }

    @Test
    void testSkipBookPagesPageCountException() {
        Reader reader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Book book = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);
        
        int pageCountStep = 200;
        reader.addBook(book);
        reader.skipBookPages(book, pageCountStep);

        Assertions.assertThrows(IllegalArgumentException.class, () -> reader.skipBookPages(book, pageCountStep));
    }

    @Test
    void testReadersAreEqual() {
        Reader firstReader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Reader secondReader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Assertions.assertEquals(firstReader, secondReader);
    }

    @Test
    void testReadersAreNotEqual() {
        Reader firstReader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Reader secondReader = new Reader(
            "Petro Petrenko", 
            "exampe456@example.com", 
            "+1111567890");

        Assertions.assertNotEquals(firstReader, secondReader);
    }
}
