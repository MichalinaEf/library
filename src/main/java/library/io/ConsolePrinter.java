package library.io;

import library.model.*;

import java.util.Collection;
import java.util.Collections;

public class ConsolePrinter {
    public void printBooks(Collection<Publication>publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No books in the library");
    }

    public void printMagazines(Collection<Publication>publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No magazines in the library");
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }
    public void printLine(String text) {
        System.out.println(text);
    }
}
