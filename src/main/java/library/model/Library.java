package library.model;

import library.exceptions.PublicationAlreadyExistsException;
import library.exceptions.UserAlreadyExistsException;
import library.model.Book;
import library.model.Magazine;
import library.model.Publication;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {

    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String,Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator) {
        ArrayList<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator) {
        ArrayList<LibraryUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public void addUser(LibraryUser user) {
        if(users.containsKey(user.getPesel()))
            throw new UserAlreadyExistsException("The user with the indicated PESEL already exists "
                    + user.getPesel()
            );
        users.put(user.getPesel(), user);
    }
    public void addPublication(Publication publication) {
        if(publications.containsKey(publication.getTitle()))
            throw new PublicationAlreadyExistsException(
                    "A publication with this title already exists " + publication.getTitle()
            );
        publications.put(publication.getTitle(), publication);
    }

    public boolean removePublication(Publication publication) {
        if(publications.containsValue(publication)) {
            publications.remove(publication.getTitle());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Publication> findPublicationByTitle(String title) {
        return Optional.ofNullable(publications.get(title));
    }

}
