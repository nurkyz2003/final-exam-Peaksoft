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
        dao.getLibrary().getLibraryMembers()
                .stream().filter(x -> Objects.equals(x.getMemberId(), id))
                .findFirst()
                .ifPresent(x -> dao.getLibrary().getLibraryMembers().remove(x));

    }

    @Override
    public void addBookToLibrary(Book book) {
        dao.getLibrary().getBooks().add(book);
    }

    @Override
    public void getLibraryBooks() {
        for (Book x : this.dao.getLibrary().getBooks()) {
            if (x.getTitle() != null) {
                System.out.println("ID " + x.getBookId());
                System.out.println("Name " + x.getTitle());
                System.out.println("Currently reading: " + x.getCurrentHolder() + "\n");

            }
        }
    }

    @Override
    public void deleteLibraryBookByID(Long id) {
        dao.getLibrary().getBooks().
                stream().filter(x -> x.getBookId() == id)
                .findFirst()
                .ifPresent(x -> dao.getLibrary().getBooks().remove(x));
    }

    @Override
    public Book findLibraryBookById(Long id) {
        return dao.getLibrary().getBooks().stream().filter(x->x.getBookId().equals(id)).findFirst().get();
    }

    @Override
    public void addBookToMember() {
        System.out.print("Введите ID участника чтобы добавить книгу: ");
        long membersID = scannerN.nextLong();
        Optional<LibraryMember> optionalMember =
                dao.getLibrary().getLibraryMembers().stream()
                        .filter(x -> x.getMemberId() == membersID)
                        .filter(x -> x.getCurrentlyReading() == null).findFirst();

        System.out.print("Введите ID книги чтобы добавить к участнику: ");
        long bookID = scannerN.nextLong();
        Optional<Book> optionalBook =
                dao.getLibrary().getBooks().stream()
                        .filter(x -> x.getBookId() == bookID)
                        .filter(x -> x.getCurrentHolder() == null).findFirst();

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
        System.out.print("Введите ID участника чтобы убрать книгу: ");
        long membersID = scannerN.nextLong();
        Optional<LibraryMember> optionalMember =
                dao.getLibrary().getLibraryMembers().stream()
                        .filter(x -> x.getMemberId() == membersID && x.getCurrentlyReading()!=null)
                        .findFirst();

        System.out.print("Введите ID книги чтобы удалить из участника: ");
        long bookID = scannerN.nextLong();
        Optional<Book> optionalBook =
                dao.getLibrary().getBooks().stream()
                        .filter(x -> x.getBookId() == bookID)
                        .filter(x -> x.getCurrentHolder() != null).findFirst();

        if(optionalMember.isEmpty()) System.out.println("Бул адам базада жок же китеби жок");
        else{
            if (optionalBook.isEmpty()) System.out.println("Бул китеп базада жок же колдонуучусу жок");
            else {
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
