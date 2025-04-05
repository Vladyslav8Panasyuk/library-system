package com.example.model;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private boolean isBorrowed;

    public Book(String title, String author, String publisher, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
        
    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrow() {
        if (isBorrowed) {
            throw new IllegalStateException("The book is already borrowed and cannot be borrowed again.");
        }
        isBorrowed = true;
    }

    public void giveBack() {
        if (!isBorrowed) {
            throw new IllegalStateException("The book has not been borrowed and cannot be given back.");
        }
        isBorrowed = false;
    }
}
