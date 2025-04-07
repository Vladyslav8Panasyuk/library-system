package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private String name;
    private String street;
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Reader> readers = new ArrayList<Reader>();

    public Library() { }

    public Library(String name, String street) {
        setName(name);
        setStreet(street);
    }

    public Library(Library library) {
        setName(library.name);
        setStreet(library.street);
        setBooks(new ArrayList<Book>(library.books));
        setReaders(new ArrayList<Reader>(library.readers));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<Reader> readers) {
        this.readers = readers;
    }

    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new IllegalArgumentException("There already is such a book.");
        } else {
            books.add(book);
        }
    }

    public Optional<Book> findBook(String title, String author, String publisher, int publicationYear) {
        return books.stream()
            .filter(book -> book.getTitle().equalsIgnoreCase(title))
            .filter(book -> book.getAuthor().equalsIgnoreCase(author))
            .filter(book -> book.getPublisher().equalsIgnoreCase(publisher))
            .filter(book -> book.getPublicationYear() == publicationYear)
            .findFirst();
    }

    public void updateBook(
        Book book, String title, String author, 
        String publisher, int publicationYear, int pageCount) {
        
        if (books.contains(book)) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setPublicationYear(publicationYear);
            book.setPageCount(pageCount);            
        } else {
            throw new IllegalArgumentException("There is no such book.");
        }
    }

    public void removeBook(Book book) {
        if (book.isBorrowed()) {
            throw new IllegalArgumentException("The book cannot be removed because it is borrowed.");
        }
        if (!books.remove(book)) {
            throw new IllegalArgumentException("There is no such book.");
        }
    }

    public void addReader(Reader reader) {
        if (readers.contains(reader)) {
            throw new IllegalArgumentException("There is already such a reader.");
        } else {
            readers.add(reader);
        }
    }

    public Optional<Reader> findReader(String name, String email, String phoneNumber) {
        return readers.stream()
            .filter(reader -> reader.getName().equalsIgnoreCase(name))
            .filter(reader -> reader.getEmail().equalsIgnoreCase(email))
            .filter(reader -> reader.getPhoneNumber().equalsIgnoreCase(phoneNumber))
            .findFirst();
    }

    public void updateReader(Reader reader, String name, String email, String phoneNumber) {
        if (readers.contains(reader)) {
            reader.setName(name);
            reader.setEmail(email);
            reader.setPhoneNumber(phoneNumber);
        } else {
            throw new IllegalArgumentException("There is no such reader.");
        }
    }

    public void removeReader(Reader reader) {
        if (readers.contains(reader) && reader.getBooks().size() > 0) {
            throw new IllegalArgumentException("The reader cannot be deleted because they have borrowed books.");
        }
        if (!readers.remove(reader)) {
            throw new IllegalArgumentException("There is no such reader.");
        }
    }

    public void borrowBook(Reader reader, Book book) {
        if (!readers.contains(reader)) {
            throw new IllegalArgumentException("No such reader has been added.");
        }
        if (!books.contains(book)) {
            throw new IllegalArgumentException("No such book has been added.");
        }
        if (book.isBorrowed()) {
            throw new IllegalStateException("The book is already borrowed and cannot be borrowed again.");
        }
        book.setBorrowed(true);
        reader.addBook(book);
    }

    public void giveBackBook(Reader reader, Book book) {
        if (!readers.contains(reader)) {
            throw new IllegalArgumentException("No such reader has been added.");
        }
        if (!books.contains(book)) {
            throw new IllegalArgumentException("No such book has been added.");
        }
        if (!book.isBorrowed()) {
            throw new IllegalStateException("The book has not been borrowed and cannot be given back.");
        }
        book.setBorrowed(false);
        reader.removeBook(book);
    }

    public List<Book> getBorrowedBooks() {
        return books.stream()
            .filter(book -> book.isBorrowed())
            .collect(Collectors.toList());
    }

    public List<Book> getUnborrowedBooks() {
        return books.stream()
            .filter(book -> !book.isBorrowed())
            .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Library library = (Library) obj;
        return Objects.equals(name, library.name) &&
            Objects.equals(street, library.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, street);
    }
}
