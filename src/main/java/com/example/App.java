package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.javatuples.Pair;

import com.example.model.Book;
import com.example.model.Library;
import com.example.model.Reader;
import com.example.service.LibraryDataManager;

public class App {
    private static final int LOWER_COMMAND_INDEX = 1; 
    private static final int UPPER_COMMAND_INDEX = 13;

    private Scanner scanner = new Scanner(System.in);

    private ArrayList<Pair<String, Comparator<Book>>> bookComparators = new ArrayList<>();
    private ArrayList<Pair<String, Comparator<Reader>>> readerComparators = new ArrayList<>();

    public App() {
        bookComparators.add(new Pair<>("Title", Comparator.comparing(Book::getTitle)));
        bookComparators.add(new Pair<>("Author", Comparator.comparing(Book::getAuthor)));
        bookComparators.add(new Pair<>("Publisher", Comparator.comparing(Book::getPublisher)));
        bookComparators.add(new Pair<>("Publication year", Comparator.comparing(Book::getPublicationYear)));
        bookComparators.add(new Pair<>("Page count", Comparator.comparing(Book::getPageCount)));
        bookComparators.add(new Pair<>("Is borrowed", Comparator.comparing(Book::isBorrowed)));
        
        readerComparators.add(new Pair<>("Name", Comparator.comparing(Reader::getName)));
        readerComparators.add(new Pair<>("Email", Comparator.comparing(Reader::getEmail)));
        readerComparators.add(new Pair<>("Phone number", Comparator.comparing(Reader::getPhoneNumber)));
    }

    public void Run() {
        Library library = createLibrary();
        boolean isRunning = true;
        while (isRunning) {
            printLibraryInformation(library);
            printCommands();
            switch (scanCommandIndex()) {
                case 1:
                    addBook(library);
                    break;

                case 2:
                    updateBook(library);
                    break;

                case 3:
                    removeBook(library);
                    break;
                    
                case 4:
                    addReader(library);
                    break;

                case 5:
                    updateReader(library);
                    break;

                case 6:
                    removeReader(library);
                    break;
                    
                case 7:
                    borrowBook(library);
                    break;

                case 8:
                    giveBackBook(library);
                    break;

                case 9:
                    printAllBooks(library.getBooks());
                    break;

                case 10:
                    printAllReaders(library.getReaders());
                    break;
                    
                case 11:
                    exportLibrary(library);
                    break;

                case 12:
                    Optional<Library> optionalLibrary = importLibrary();
                    if (optionalLibrary.isPresent()) {
                        library = optionalLibrary.get();
                    }
                    break;

                case 13:
                    System.err.println("Program completed!");
                    isRunning = false;
                    break;
            
                default:
                    break;
            }
        }
    }

    private void printCommands() {
        System.out.println("=== Commands ===");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Add Reader");
        System.out.println("5. Update Reader");
        System.out.println("6. Remove Reader");
        System.out.println("7. Borrow Book");
        System.out.println("8. Give back Book");
        System.out.println("9. Show All Books");
        System.out.println("10. Show All Readers");
        System.out.println("11. Export Library to JSON");
        System.out.println("12. Import Library from JSON");
        System.out.println("13. Exit");
        System.out.println("");
    }

    private int scanCommandIndex() {
        System.out.print(String.format("Please select a command (%d-%d): ", LOWER_COMMAND_INDEX, UPPER_COMMAND_INDEX));

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }

        int commandIndex = scanner.nextInt();
        while (commandIndex < LOWER_COMMAND_INDEX || commandIndex > UPPER_COMMAND_INDEX) {
            System.out.print(String.format("Please enter the command index from %d to %d: ", LOWER_COMMAND_INDEX, UPPER_COMMAND_INDEX));
            commandIndex = scanner.nextInt();
        }

        System.out.println("");

        return commandIndex;
    }

    private Library createLibrary() {
        Library library = new Library();

        System.out.println("--- Create a library ---");

        System.out.print("Please enter a name: ");
        library.setName(scanner.nextLine());

        System.out.print("Please enter a street: ");
        library.setStreet(scanner.nextLine());
        
        System.out.println("The library was successfully created!");
        System.out.println("");

        return library;
    }

    private void printLibraryInformation(Library library) {
        System.out.println("--- Library ---");
        System.out.println("Name: " + library.getName());
        System.out.println("Street: " + library.getStreet());
        System.out.println("");
    }

    private void addBook(Library library) {
        library.addBook(createBook());
        System.out.println("The book has been successfully added to the library.");
        System.out.println("");
    }

    private Book createBook() {
        Book book = new Book();

        System.out.println("--- Create a Book ---");
        
        System.out.print("Please enter a tittle: ");
        scanner.nextLine();
        book.setTitle(scanner.nextLine());

        System.out.print("Please enter an author: ");
        book.setAuthor(scanner.nextLine());

        System.out.print("Please enter a publisher: ");
        book.setPublisher(scanner.nextLine());

        while (true) {
            System.out.print("Please enter a publication year: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            try {
                book.setPublicationYear(scanner.nextInt());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.print("Please enter a page count: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            try {
                book.setPageCount(scanner.nextInt());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("The book was successfully created!");
        System.out.println("");

        return book;
    }

    private void updateBook(Library library) {
        Optional<Book> book = findBook(library);
        if (book.isEmpty()) {
            System.out.println("Unfortunately, such a book was not found in the library.");
            System.out.println("");
            return;
        }
        printBookInformation(book.get());
        
        System.out.println("--- Update a Book ---");
        
        System.out.print("Please enter a tittle: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        
        System.out.print("Please enter an author: ");
        String author = scanner.nextLine();

        System.out.print("Please enter a publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Please enter a publication year: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int publicationYear = scanner.nextInt();

        System.out.print("Please enter a page count: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int pageCount = scanner.nextInt();
        
        System.out.println("");

        try {
            library.updateBook(book.get(), title, author, publisher, publicationYear, pageCount);
        System.out.println("The book was successfully updated!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to update a book.");
        }
        System.out.println("");
    }

    private void removeBook(Library library) {
        Optional<Book> book = findBook(library);
        if (book.isEmpty()) {
            System.out.println("Unfortunately, such a book was not found in the library.");
        } else {
            try {
                library.removeBook(book.get());
                System.out.println("Book successfully removed from library!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Unable to remove book from library.");
            }
        }
        System.out.println("");
    }

    private Optional<Book> findBook(Library library) {
        System.out.println("--- Find a Book ---");

        System.out.print("Please enter a tittle: ");
        scanner.nextLine();
        String title = scanner.nextLine();

        System.out.print("Please enter an author: ");
        String author = scanner.nextLine();

        System.out.print("Please enter a publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Please enter a publication year: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int publicationYear = scanner.nextInt();

        System.out.println("");

        return library.findBook(title, author, publisher, publicationYear);
    }

    private void printBookInformation(Book book) {
        System.out.println("--- Book ---");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publication year: " + book.getPublicationYear());
        System.out.println("Page count: " + book.getPageCount());
        System.out.println("Current page number: " + book.getCurrentPageNumber());
        System.out.println("Is borrowed: " + book.isBorrowed());
        System.out.println("");
    }

    private void printAllBooks(List<Book> books) {
        System.out.println("=== All Books ===");
        System.out.println("");
        for (Book book : books) {
            printBookInformation(book);
        }
    }
    
    private void addReader(Library library) {
        try {
            library.addReader(createReader());
            System.out.println("The reader has been successfully added to the library!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to add reader to library.");
        }
        System.out.println("");
    }

    private Reader createReader() {
        Reader reader = new Reader();

        System.out.println("--- Create a Reader ---");
        
        System.out.print("Please enter a name: ");
        scanner.nextLine();
        reader.setName(scanner.nextLine());

        while (true) {
            System.out.print("Please enter an email: ");
            try {
                reader.setEmail(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.print("Please enter a phone number: ");
            try {
                reader.setPhoneNumber(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("The reader was successfully created!");
        System.out.println("");

        return reader;
    }

    private void updateReader(Library library) {
        Optional<Reader> reader = findReader(library);
        if (reader.isEmpty()) {
            System.out.println("Unfortunately, such a reader was not found in the library.");
            System.out.println("");
            return;
        }
        printReaderInformation(reader.get());
        
        System.out.println("--- Update a Reader ---");
        
        System.out.print("Please enter a name: ");
        String name = scanner.nextLine();
        
        System.out.print("Please enter an email: ");
        String email = scanner.nextLine();

        System.out.print("Please enter a phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("");

        try {
            library.updateReader(reader.get(), name, email, phoneNumber);
        System.out.println("The reader was successfully updated!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to update a reader.");
        }
        System.out.println("");
    }

    private void removeReader(Library library) {
        Optional<Reader> reader = findReader(library);
        if (reader.isEmpty()) {
            System.out.println("Unfortunately, such a reader was not found in the library.");
        } else {
            try {
                library.removeReader(reader.get());
                System.out.println("Reader successfully removed from library!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Unable to remove reader from library.");
            }
        }
        System.out.println("");
    }

    private Optional<Reader> findReader(Library library) {
        System.out.println("--- Find a Reader ---");

        System.out.print("Please enter a name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Please enter an email: ");
        String email = scanner.nextLine();

        System.out.print("Please enter a phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("");

        return library.findReader(name, email, phoneNumber);
    }

    private void printReaderInformation(Reader reader) {
        System.out.println("--- Reader ---");
        System.out.println("Name: " + reader.getName());
        System.out.println("Email: " + reader.getEmail());
        System.out.println("Phone number: " + reader.getPhoneNumber());
        System.out.println("");
    }

    private void printAllReaders(List<Reader> readers) {
        System.out.println("=== All Readers ===");
        System.out.println("");
        for (Reader reader : readers) {
            printReaderInformation(reader);
        }
    }

    private void borrowBook(Library library) {
        Optional<Book> book = findBook(library);
        if (book.isEmpty()) {
            System.out.println("Unfortunately, such a book was not found in the library.");
            System.out.println("");
            return;
        } 
        
        Optional<Reader> reader = findReader(library);
        if (reader.isEmpty()) {
            System.out.println("Unfortunately, such a reader was not found in the library.");
            System.out.println("");
            return;
        } 
        
        try {
            library.borrowBook(reader.get(), book.get());
            System.out.println("The book was successfully borrowed to the reader!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to borrow the book to the reader.");
        }
        System.out.println("");
    }

    private void giveBackBook(Library library) {
        Optional<Book> book = findBook(library);
        if (book.isEmpty()) {
            System.out.println("Unfortunately, such a book was not found in the library.");
            System.out.println("");
            return;
        } 
        
        Optional<Reader> reader = findReader(library);
        if (reader.isEmpty()) {
            System.out.println("Unfortunately, such a reader was not found in the library.");
            System.out.println("");
            return;
        } 
        
        try {
            library.giveBackBook(reader.get(), book.get());
            System.out.println("The book was successfully returned by the reader!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Unable to give back the book from the reader.");
        }
        System.out.println("");
    }

    private void exportLibrary(Library library) {
        System.out.println("=== Library export ===");
        System.out.println("");

        System.out.print("Enter the path to the file: ");
        scanner.nextLine();
        String filePath = scanner.nextLine();
        System.out.println("");
    
        System.out.println("--- Book comparator ---");
        for (int i = 0; i < bookComparators.size(); i++) {
            System.out.println((i + 1) + ". " + bookComparators.get(i).getValue0());
        }
        System.out.println("");
        
        int bookComparatorIndex;
        while (true) {
            System.out.print("Enter the Book comparator index: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            bookComparatorIndex = scanner.nextInt();
            if (bookComparatorIndex < 1 || bookComparatorIndex > bookComparators.size()) {
                System.out.println("The index must be between 1 and " + bookComparators.size());
            } else {
                break;
            }
        }
        System.out.println("");

        System.out.println("--- Reader comparator ---");
        for (int i = 0; i < readerComparators.size(); i++) {
            System.out.println((i + 1) + ". " + readerComparators.get(i).getValue0());
        }
        System.out.println("");

        int readerComparatorIndex;
        while (true) {
            System.out.print("Enter the Reader comparator index: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            readerComparatorIndex = scanner.nextInt();
            if (readerComparatorIndex < 1 || readerComparatorIndex > readerComparators.size()) {
                System.out.println("The index must be between 1 and " + readerComparators.size());
            } else {
                break;
            }
        }
        System.out.println("");

        Comparator<Book> bookComparator = bookComparators.get(bookComparatorIndex - 1).getValue1();
        Comparator<Reader> readerComparator = readerComparators.get(readerComparatorIndex - 1).getValue1();

        try {
            LibraryDataManager.exportLibrary(library, filePath, bookComparator, readerComparator);
            System.out.println("Library successfully exported!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to export library.");
        }
        System.out.println("");
    }

    private Optional<Library> importLibrary() {
        System.out.println("=== Library import ===");
        System.out.println("");

        System.out.print("Enter the path to the file: ");
        scanner.nextLine();
        String filePath = scanner.nextLine();
        System.out.println("");

        Optional<Library> library = Optional.empty();
        try {
            library = Optional.of(LibraryDataManager.importLibrary(filePath));
            System.out.println("Library successfully imported!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to import library.");
        }
        System.out.println("");

        return library;
    }
}
