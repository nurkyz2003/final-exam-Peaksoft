package libraryProject.service.impl;

import libraryProject.dao.Dao;
import libraryProject.model.Book;
import libraryProject.model.LibraryMember;
import libraryProject.service.LibraryService;

import java.util.List;
import java.util.Objects;
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
        return dao.getLibrary().getLibraryMembers();
    }

    @Override
    public void addLibraryMember(LibraryMember member) {
        this.dao.getLibrary().getLibraryMembers().add(member);
    }

    @Override
    public LibraryMember findLibraryMemberById(Long id) {
        return  dao.getLibrary().getLibraryMembers().stream().filter(x -> x.getMemberId() == id).findFirst().get();
    }

    @Override
    public void deleteLibraryMemberByID(Long id) {
        dao.getLibrary().getLibraryMembers().stream().filter(x -> Objects.equals(x.getMemberId(), id)).findFirst().ifPresent(x -> dao.getLibrary().getLibraryMembers().remove(x));
    }

    @Override
    public void addBookToLibrary(Book book) {
        dao.getLibrary().getBooks().add(book);
    }

    @Override
    public void getLibraryBooks() {
        for (Book book : this.dao.getLibrary().getBooks()) {
            if (book.getTitle() != null) {
                System.out.println("ID: " + book.getBookId());
                System.out.println("Name: " + book.getTitle());
                System.out.println("Currently Reading Books: " + book.getCurrentHolder() + "\n");
            }
        }
    }

    @Override
    public void deleteLibraryBookByID(Long id) {
        dao.getLibrary().getBooks().stream().filter(x -> Objects.equals(x.getBookId(), id)).findFirst().ifPresent(x -> dao.getLibrary().getBooks().remove(x));
    }

    @Override
    public Book findLibraryBookById(Long id) {
        return dao.getLibrary().getBooks().stream().filter(x->x.getBookId().equals(id)).findFirst().get();
    }

    @Override
    public void addBookToMember() {
        System.out.print("Enter the member's ID to add book: ");
        long membersID = scannerN.nextLong();
        Optional<LibraryMember> optionalMember = dao.getLibrary().getLibraryMembers().stream().filter(x -> x.getMemberId() == membersID).filter(x -> x.getCurrentlyReading() == null).findFirst();
        System.out.print("Enter the book's ID to member: ");
        long bookID = scannerN.nextLong();
        Optional<Book> optionalBook = dao.getLibrary().getBooks().stream().filter(x -> x.getBookId() == bookID).filter(x -> x.getCurrentHolder() == null).findFirst();
        if (optionalMember.isPresent() && optionalBook.isPresent() ) {
            LibraryMember member = optionalMember.get();
            Book book = optionalBook.get();
            if(member.getCurrentlyReading() == null && book.getCurrentHolder() == null){
                deleteLibraryMemberByID(membersID);
                deleteLibraryBookByID(bookID);
                book.setCurrentHolder(member);
                member.setCurrentlyReading(book);
                addLibraryMember(member);
                addBookToLibrary(book);
            }
        }
    }

    @Override
    public void removeBookFromReading(){
        System.out.print("Enter the member's ID to delete book: ");
        long membersID = scannerN.nextLong();
        Optional<LibraryMember> optionalMember = dao.getLibrary().getLibraryMembers().stream().filter(x -> x.getMemberId() == membersID && x.getCurrentlyReading()!=null).findFirst();
        System.out.print("Enter the book's ID to delete in member: ");
        long bookID = scannerN.nextLong();
        Optional<Book> optionalBook = dao.getLibrary().getBooks().stream().filter(x -> x.getBookId() == bookID).filter(x -> x.getCurrentHolder() != null).findFirst();
        if(optionalMember.isEmpty()) System.out.println("We don't find this member!");
        else{
            if (optionalBook.isEmpty()) System.out.println("We don't find this book!");
            else{
                LibraryMember member = optionalMember.get();
                Book book = optionalBook.get();
                member.getFinishedBooks().add(member.getCurrentlyReading());
                member.setCurrentlyReading(null);
                book.setCurrentHolder(null);
                deleteLibraryMemberByID(membersID);
                deleteLibraryBookByID(bookID);
                addLibraryMember(member);
                addBookToLibrary(book);
            }
        }
    }
}
