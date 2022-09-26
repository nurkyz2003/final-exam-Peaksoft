package test.service.impl;

import test.dao.Dao;
import test.enums.Status;
import test.model.Book;
import test.model.LibraryMember;
import test.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LibraryServiceImpl implements LibraryService {
    private static final Scanner scannerN = new Scanner(System.in);
    private Dao dao;

    public LibraryServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public List<LibraryMember> getLibraryMembers() {
        return null;
    }

    @Override
    public void addLibraryMember(LibraryMember member) {

    }

    @Override
    public LibraryMember findLibraryMemberById(Long id) {
        return null;
    }

    @Override
    public void deleteLibraryMemberByID(Long id) {

    }

    @Override
    public void addBookToLibrary(Book book) {

    }

    @Override
    public List<Book> getLibraryBooks() {
        return null;
    }

    @Override
    public void deleteLibraryBookByID(Long id) {

    }

    @Override
    public Book findLibraryBookById(Long id) {
        return null;
    }

    @Override
    public void addBookToMember() {

    }

    @Override
    public void removeBookFromReading(){

    }
}
