package service;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Book;
import com.example.model.Library;
import com.example.model.Reader;
import com.example.service.LibraryDataManager;

public class LibraryDataManagerTest {
    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();

        Book firstBook = new Book(
            "Harry Potter and the Prisoner of Azkaban", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1999,
            435);

        Book secondBook = new Book(
            "Harry Potter and the Philosopher's Stone", 
            "Joanne Rowling", 
            "Scholastic Corporation", 
            1997,
            309);

        Reader firstReader = new Reader(
            "Ivan Ivanenko", 
            "exampe123@example.com", 
            "+1234567890");

        Reader secondReader = new Reader(
            "Petro Petrenko", 
            "exampe456@example.com", 
            "+1111567890");

        library.addBook(firstBook);
        library.addBook(secondBook);

        library.addReader(firstReader);
        library.addReader(secondReader);
    }

    @Test
    void testImportLibraryMock() throws IOException {
        MockedStatic<LibraryDataManager> libraryDataManagerMock = Mockito.mockStatic(LibraryDataManager.class);
        
        libraryDataManagerMock.when(() -> LibraryDataManager.importLibrary(Mockito.anyString())).thenReturn(library);
        
        Library importedLibrary = LibraryDataManager.importLibrary("test.json");

        Assertions.assertEquals(library, importedLibrary);
    }

    @Test
    void testExportImportLibrary() throws IOException {
        String filePath = "test_library.json";

        LibraryDataManager.exportLibrary(library, filePath, Comparator.comparing(Book::getPublicationYear), Comparator.comparing(Reader::getName));
        Library importedLibrary = LibraryDataManager.importLibrary(filePath);

        Assertions.assertNotNull(importedLibrary);
        Assertions.assertEquals(library.getBooks().size(), importedLibrary.getBooks().size());
        Assertions.assertEquals(library.getReaders().size(), importedLibrary.getReaders().size());

        Book firstBook = library.getBooks().stream().findFirst().get();
        Book importedFirstBook = importedLibrary.getBooks().stream().findFirst().get();
        Assertions.assertNotEquals(firstBook, importedFirstBook);
    }

    @Test
    void testExportLibraryException() {
        Library emptyLibrary = new Library();

        Assertions.assertThrows(IOException.class, () -> 
            LibraryDataManager.exportLibrary(
                emptyLibrary,
                "",
                Comparator.comparing(Book::getTitle),
                Comparator.comparing(Reader::getName)));
    }

    @Test
    void testImportLibraryException() {
        Assertions.assertThrows(IOException.class, () -> 
            LibraryDataManager.importLibrary(""));
    }
}
