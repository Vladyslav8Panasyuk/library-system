package com.example.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.model.Book;
import com.example.model.Library;
import com.example.model.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class LibraryDataManager {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void exportLibrary(
        Library library, String filePath, 
        Comparator<Book> bookComparator, Comparator<Reader> readerComparator) throws IOException {
        
            Library libraryCopy = new Library(library);
        
        ArrayList<Book> sortedBooks = libraryCopy.getBooks().stream()
            .sorted(bookComparator)
            .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Reader> sortedReaders = libraryCopy.getReaders().stream()
            .sorted(readerComparator)
            .collect(Collectors.toCollection(ArrayList::new));
        
        libraryCopy.setBooks(sortedBooks);
        libraryCopy.setReaders(sortedReaders);

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(new File(filePath), libraryCopy);
    }

    public static Library importLibrary(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), Library.class);
    }
}
