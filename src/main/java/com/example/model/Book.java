package com.example.model;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int pageCount;
    private int currentPageNumber = 1;
    private boolean isBorrowed;

    public Book(String title, String author, String publisher, int publicationYear, int pageCount) {
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setPublicationYear(publicationYear);
        setPageCount(pageCount);
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
        if (publicationYear <= 0) {
            throw new IllegalArgumentException("The publication year must be greater than zero.");
        } 
        
        int currentYear = java.time.Year.now().getValue();
        if (publicationYear > currentYear) {
            throw new IllegalArgumentException("The publication year must not be older than the current year.");
        }
        
        this.publicationYear = publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("The page count must be greater than zero.");
        }
        this.pageCount = pageCount;
    }
    
    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public void nextPage() {
        if (currentPageNumber < pageCount) {
            currentPageNumber++;
        } else {
            throw new IllegalStateException("This is the last page.");
        }
    }

    public void previousPage() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
        } else {
            throw new IllegalStateException("This is the first page.");
        }
    }

    public void goToPage(int pageNumber) {
        if (pageNumber < 1 || pageNumber > pageCount) {
            throw new IllegalArgumentException(String.format("The page number must be between 1 and %d.", pageCount));
        }
        this.currentPageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Book book = (Book) obj;
                
        return pageCount == book.pageCount &&
            publicationYear == book.publicationYear &&
            Objects.equals(title, book.title) &&
            Objects.equals(author, book.author) &&
            Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publisher, publicationYear, pageCount);
    }
}
