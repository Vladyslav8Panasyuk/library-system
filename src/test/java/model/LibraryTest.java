package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.model.Book;
import com.example.model.Library;
import com.example.model.Reader;

public class LibraryTest {
    
    @Test
    void testBorrowBook() {
        Library library = new Library(
            "Main", 
            "Main street");

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

        library.addBook(book);
        library.addReader(reader);
        library.borrowBook(reader, book);

        Assertions.assertTrue(book.isBorrowed() && reader.getBooks().contains(book));
    }

    @Test
    void testBorrowBookReborrowingException() {
        Library library = new Library(
            "Main", 
            "Main street");

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

        library.addBook(book);
        library.addReader(reader);
        library.borrowBook(reader, book);

        Assertions.assertThrows(IllegalStateException.class, () -> library.borrowBook(reader, book));
    }

    @Test
    void testGiveBackBook() {
        Library library = new Library(
            "Main", 
            "Main street");

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

        library.addBook(book);
        library.addReader(reader);
        library.borrowBook(reader, book);
        library.giveBackBook(reader, book);

        Assertions.assertTrue(!book.isBorrowed() && !reader.getBooks().contains(book));
    }

    @Test
    void testGiveBackBookUnborrowedException() {
        Library library = new Library(
            "Main", 
            "Main street");

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

        library.addBook(book);
        library.addReader(reader);

        Assertions.assertThrows(IllegalStateException.class, () -> library.giveBackBook(reader, book));
    }

    @Test
    void testLibrariesAreEqual() {
        Library firstLibrary = new Library(
            "Main", 
            "Main street");

        Library secondLibrary = new Library(
            "Main", 
            "Main street");

        Assertions.assertEquals(firstLibrary, secondLibrary);
    }

    @Test
    void testLibrariesAreNotEqual() {
        Library firstLibrary = new Library(
            "Main", 
            "Main street");

        Library secondLibrary = new Library(
            "Wall", 
            "Wall street");

        Assertions.assertNotEquals(firstLibrary, secondLibrary);
    }
}
