package test.service;

import test.model.Book;
import test.model.LibraryMember;

import java.util.List;

public interface LibraryService {
    List<LibraryMember> getLibraryMembers();
    void addLibraryMember(LibraryMember member);

    LibraryMember findLibraryMemberById(Long id);
    void deleteLibraryMemberByID(Long id);

    void addBookToLibrary(Book book);

    List<Book> getLibraryBooks();

    Book findLibraryBookById(Long id);

    void deleteLibraryBookByID(Long id);

    void addBookToMember();

    void removeBookFromReading();
}
