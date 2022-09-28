package libraryProject;

import libraryProject.dao.Dao;
import libraryProject.model.Book;
import libraryProject.model.Library;
import libraryProject.model.LibraryMember;
import libraryProject.service.impl.LibraryServiceImpl;

import java.util.Scanner;

public class Main {
    static Scanner scannerN = new Scanner(System.in);
    static Scanner scannerS = new Scanner(System.in);
    public static void main(String[] args) {
        Dao dao = new Dao(new Library());
        LibraryServiceImpl libraryService = new LibraryServiceImpl(dao);
        while(true){
            buttons();
            System.out.println("Choose the function: ");
            String button = scannerN.nextLine();
            switch (button) {
                case "1" -> {
                    LibraryMember newLibraryMember = new LibraryMember();
                    System.out.println("Enter the new member's ID: ");
                    newLibraryMember.setMemberId(scannerS.nextLong());
                    System.out.println("Enter the new member's name: ");
                    newLibraryMember.setName(scannerN.nextLine());

                    libraryService.addLibraryMember(newLibraryMember);
                }
                case "2" -> System.out.println(libraryService.getLibraryMembers());
                case "3" -> {
                    LibraryMember libraryMember = libraryService.findLibraryMemberById(scannerS.nextLong());
                    System.out.println("Current book \n");
                    System.out.println(libraryMember.getCurrentlyReading());
                    System.out.println("Borrowed books \n");
                    System.out.println(libraryMember.getFinishedBooks());
                }
                case "4" -> {
                    System.out.println("Enter the member's ID: ");
                    long id = scannerS.nextLong();
                    libraryService.deleteLibraryMemberByID(id);
                }
                case "5" -> {
                    Book book = new Book();
                    System.out.println("Enter the book's ID: ");
                    book.setBookId(scannerS.nextLong());
                    System.out.println("Enter the book's title");
                    book.setTitle(scannerN.nextLine());
                    libraryService.addBookToLibrary(book);
                }
                case "6" -> libraryService.getLibraryBooks();
                case "7" -> System.out.println(libraryService.findLibraryBookById(scannerS.nextLong()));
                case "8" -> {
                    System.out.println("Enter the book ID to delete: ");
                    long id = scannerS.nextLong();
                    libraryService.deleteLibraryBookByID(id);
                }
                case "9" -> libraryService.addBookToMember();
                case "10" -> libraryService.removeBookFromReading();
            }
                if (button.equals("x")) break;
        }
    }

    public static void  buttons(){
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("Нажмите 1, чтобы добавить нового участника в библиотеку.");
        System.out.println("Нажмите 2, чтобы увидеть всех участников библиотеки.");
        System.out.println("Нажмите 3, чтобы найти участника по ID и увидеть данные участника, читаемая книга и прочитанные.");
        System.out.println("Нажмите 4, чтобы удалить участника по ID.");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("Нажмите 5, чтобы добавить книгу в библиотеку.");
        System.out.println("Нажмите 6, чтобы увидеть все книги в библиотеке.");
        System.out.println("Нажмите 7, чтобы найти книгу по ID.");
        System.out.println("Нажмите 8, чтобы удалить книгу по ID.");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("Нажмите 9, чтобы ввести memberId участника и bookId книги, добавить в читаемые");
        System.out.println("Нажмите 10, чтобы ввести memberId , и добавить его читаемую книгу в прочитанные");
        System.out.println("Нажмите x, чтобы завершить программу.");
    }

}
