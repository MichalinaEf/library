package library.io;

import library.model.Book;
import library.model.LibraryUser;
import library.model.Magazine;

import java.util.Scanner;

public class DataReader {

    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }

    public Book readAndCreateBook() {
        printer.printLine("Title: ");
        String title = sc.nextLine();
        printer.printLine("Author: ");
        String author = sc.nextLine();
        printer.printLine("Publishing house: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        printer.printLine("Publication date: ");
        int releaseDate = getInt();
        printer.printLine("Pages: ");
        int pages = getInt();

        return new Book(title, author, releaseDate, pages, publisher, isbn);
    }


    public Magazine readAndCreateMagazine() {
        printer.printLine("Title: ");
        String title = sc.nextLine();
        printer.printLine("Publishing house: ");
        String publisher = sc.nextLine();
        printer.printLine("Language: ");
        String language = sc.nextLine();
        printer.printLine("Publication year: ");
        int year = getInt();
        printer.printLine("Publication month: ");
        int month = getInt();
        printer.printLine("Publication day: ");
        int day = getInt();

        return new Magazine(title, publisher, language, year, month, day);
    }

    public LibraryUser createLibraryUser() {
        printer.printLine("Name");
        String firstName = sc.nextLine();
        printer.printLine("Surname");
        String lastName = sc.nextLine();
        printer.printLine("PESEL");
        String pesel = sc.nextLine();
        return new LibraryUser(firstName, lastName, pesel);
    }

}
