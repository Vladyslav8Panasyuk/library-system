package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Reader {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
    
    private String name;
    private String email;
    private String phoneNumber;
    private ArrayList<Book> books;

    public Reader() { }

    public Reader(String name, String email, String phoneNumber) {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        books = new ArrayList<Book>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.goToFirstPage();
        books.add(book);
    }

    public void removeBook(Book book) {
        if (books.remove(book)) {
            book.goToFirstPage();
        } else {
            throw new IllegalArgumentException("There is no such book.");
        }
    }

    public void readBook(Book book, int pageCount) {
        if (!books.contains(book)) {
            throw new IllegalArgumentException("There is no such book to read.");
        }
        if (pageCount > book.getPageCount() - book.getCurrentPageNumber()) {
            throw new IllegalArgumentException("The number of pages to read exceeds the number of pages remaining.");
        }
        for (int i = 0; i < pageCount; i++) {
            book.nextPage();
        }
    }

    public void readPreviousBookPages(Book book, int pageCount) {
        if (!books.contains(book)) {
            throw new IllegalArgumentException("There is no such book to read.");
        }
        if (pageCount >= book.getCurrentPageNumber()) {
            throw new IllegalArgumentException("The number of pages to read exceeds the number of previous pages.");
        }
        for (int i = 0; i < pageCount; i++) {
            book.previousPage();
        }
    }

    public void skipBookPages(Book book, int pageCount) {
        if (!books.contains(book)) {
            throw new IllegalArgumentException("There is no such book to read.");
        }
        if (pageCount > book.getPageCount() - book.getCurrentPageNumber()) {
            throw new IllegalArgumentException("The number of pages to skip exceeds the number of pages remaining.");
        }
        book.goToPage(book.getCurrentPageNumber() + pageCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Reader reader = (Reader) obj;
        return Objects.equals(name, reader.name) &&
            Objects.equals(email, reader.email) &&
            Objects.equals(phoneNumber, reader.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber);
    }
}
