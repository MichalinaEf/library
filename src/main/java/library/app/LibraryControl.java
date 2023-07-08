package library.app;

import library.exceptions.*;
import library.io.file.FileManager;
import library.io.file.FileManagerBuilder;
import library.io.ConsolePrinter;
import library.io.DataReader;
import library.model.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {

    // variable to communicate with the user
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    // "library" that holds data
    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Imported data from a file");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("A new database has been initiated.");
            library = new Library();
        }
    }
    //The main method of the program that allows you to select options and interact
    public void controlLoop() {

        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_BOOK:
                    findBook();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("There is no such an option, please try again ");
            }
        } while(option != Option.EXIT);
    }

    private void findBook() {
        printer.printLine("Enter the title of the publication:");
        String title = dataReader.getString();
        String notFoundMessage = "There are no publications with this title";
        library.findPublicationByTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(System.out::println, () -> System.out.println(notFoundMessage));
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", try again");
            } catch (InputMismatchException ignored) {
                printer.printLine("You entered a value that is not a number, please try again:");
            }
        }
        return option;
    }


    private void printOptions() {
        printer.printLine("Choose an option: ");
        for (Option option : Option.values()){
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create book, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Capacity limit reached, no more book can be added");
        }
    }
    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create book, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Capacity limit reached, no more magazine can be added");
        }
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }
    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Magazine has been removed");
            else
                printer.printLine("There is no such magazine");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to delete magazine, invalid data");
        }

    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Book has been removed");
            else
                printer.printLine("There is no such book");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to delete book, invalid data");
        }
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printUsers() {
        printer.printUsers(library.getSortedUsers(
                Comparator.comparing(User::getLastName,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Successfully exported data to a file");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("End of the application, bye!");

    }

    private enum Option {
        EXIT(0, "Exit"),
        ADD_BOOK(1, "Add new book"),
        ADD_MAGAZINE(2,"Add new magazine"),
        PRINT_BOOKS(3, "Display available books"),
        PRINT_MAGAZINES(4, "Display available magazines"),
        DELETE_BOOK(5, "Delete book" ),
        DELETE_MAGAZINE(6, "Delete magazine" ),
        ADD_USER(7,"Add new user" ),
        PRINT_USERS(8,"Display users" ), 
        FIND_BOOK(9,"Find a book" );
        

        private int value;
        private String description;

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("There is no option id " + option);
            }
        }
    }
}
