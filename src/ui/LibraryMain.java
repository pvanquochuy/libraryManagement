package ui;

import entity.Book;
import entity.CD;
import service.FileProcess;
import service.LibraryServiceManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class LibraryMain {
    public static void main(String[] args) {
        LibraryMain main = new LibraryMain();
        main.start();
    }
    private LibraryServiceManagement<Book> bookLibrary;
    private LibraryServiceManagement<CD> cdLibrary;
    private Scanner scanner;
    boolean running = true;

    public LibraryMain() {
        bookLibrary = new LibraryServiceManagement<>();
        cdLibrary = new LibraryServiceManagement<>();
        scanner = new Scanner(System.in);
    }

    public void start(){
        Map<Integer, Runnable> menuActions = new HashMap<>();
        menuActions.put(1, () -> addBook());
        menuActions.put(2, () -> addCD());
        menuActions.put(3, () -> removeItem());
        menuActions.put(4, () -> updateItem());
        menuActions.put(5, () -> searchItem());
        menuActions.put(6, () -> borrowItem());
        menuActions.put(7, () -> returnItem());
        menuActions.put(8, () -> displayAllItems());
        menuActions.put(9, () -> saveData());
        menuActions.put(10, () -> readData());
//        menuActions.put(0, () -> exit());

        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            menuActions.getOrDefault(choice, this::invalidOption).run();
            if (choice == 0) running = false;
        }
    }
    private void displayMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Add CD");
        System.out.println("3. Remove Item");
        System.out.println("4. Update Item");
        System.out.println("5. Search Item");
        System.out.println("6. Borrow Item");
        System.out.println("7. Return Item");
        System.out.println("8. Display All Items");
        System.out.println("9. Save Data to file");
        System.out.println("10. Read Data to file");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    private void addBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Publicsher:" );
        String publicsher = scanner.nextLine();
        System.out.print("Enter Year:" );
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        Book book = new Book(id, title, publicsher, year ,author);
        bookLibrary.addItem(book);
        System.out.println("Book added successfully.");
    }
    private void addCD() {
        System.out.print("Enter CD ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter CD Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Publicsher:" );
        String publicsher = scanner.nextLine();
        System.out.print("Enter Year:" );
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Artist: ");
        String artist = scanner.nextLine();
        CD cd = new CD(id, title, publicsher, year, artist);
        cdLibrary.addItem(cd);
        System.out.println("CD added successfully.");
    }
    private void displayAllItems() {
        System.out.println("All Items in the Library:");
        bookLibrary.getAllItems().forEach(System.out::println);
        cdLibrary.getAllItems().forEach(System.out::println);
    }
    private void removeItem() {

            System.out.println("Enter item type to remove (enter 'book' or 'cd'):  ");
            String type = scanner.nextLine();
            if(type.equalsIgnoreCase("book")) {
                System.out.print("Enter Item ID to remove: ");
                String id = scanner.nextLine();
                bookLibrary.removeItem(id);
            }
            else if(type.equalsIgnoreCase("cd")) {
                System.out.print("Enter Item ID to remove: ");
                String id = scanner.nextLine();
                cdLibrary.removeItem(id);

            }
    }
    private void updateItem() {
        System.out.println("Enter item type to update (enter 'book' or 'cd'):  ");
        String type = scanner.nextLine();
        if(type.equalsIgnoreCase("book")) {
            System.out.print("Enter Item ID to update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new Book Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new publicsher: ");
            String publicsher = scanner.nextLine();
            System.out.print("Enter new year: ");
            int year = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new Author: ");
            String author = scanner.nextLine();

            Book newBook = new Book(id, title, publicsher, year , author);
                bookLibrary.updateItem(id, newBook);

        }else if (type.equalsIgnoreCase("cd")) {
            System.out.print("Enter Item ID to update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new CD Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new publicsher: ");
            String publicsher = scanner.nextLine();
            System.out.print("Enter new year: ");
            int year = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new Artist: ");
            String artist = scanner.nextLine();
            CD newCD = new CD(id, title, publicsher, year ,artist);
                cdLibrary.updateItem(id, newCD);
                System.out.println("CD updated successfully.");

        }else {
            System.out.println("Invalid type. Please try again!");
        }
    }
    private void searchItem() {
        System.out.println("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        List<Book> books = bookLibrary.searchItem(keyword);
        List<CD> cds = cdLibrary.searchItem(keyword);
        books.forEach(System.out::println);
        cds.forEach(System.out::println);
    }
    private void borrowItem() {
        System.out.println("Enter item type to borrow (enter 'book' or 'cd'):  ");
        String type = scanner.nextLine();
        if(type.equalsIgnoreCase("book")) {
            System.out.print("Enter Item ID to borrow: ");
            String id = scanner.nextLine();
                bookLibrary.borrowItem(id);

        }else if(type.equalsIgnoreCase("cd")) {
            System.out.print("Enter Item ID to borrow: ");
            String id = scanner.nextLine();
                cdLibrary.borrowItem(id);
        }
    }
    public void returnItem(){
        System.out.println("Enter item type to return (enter 'book' or 'cd'):  ");
        String type = scanner.nextLine();
        if(type.equalsIgnoreCase("book")) {
            System.out.print("Enter Item ID to return: ");
            String id = scanner.nextLine();
                bookLibrary.returnItem(id);

        }else if(type.equalsIgnoreCase("cd")) {
            System.out.print("Enter Item ID to return: ");
            String id = scanner.nextLine();
                cdLibrary.returnItem(id);
        }
    }
    private void saveData() {
        bookLibrary.saveItemsToFile("bookdata.txt").join();
        cdLibrary.saveItemsToFile("cddata.txt").join();
        System.out.println("save successfully!");
    }
    private void readData() {
        CompletableFuture<List<String>> bookData = FileProcess.readFromFileAsync("bookdata.txt");
        bookData.thenAccept(lines -> {
            System.out.println("Data from file bookdata.txt + :");
            for (String line : lines) {
                System.out.println(line);
            }
        }).join();
        CompletableFuture<List<String>> cdData = FileProcess.readFromFileAsync("cddata.txt");
        cdData.thenAccept(lines -> {
            System.out.println("Data from file cddata.txt + :");
            for (String line : lines) {
                System.out.println(line);
            }
        }).join();
    }
    private static void exit() {
        System.out.println("Exiting the system...");
//		        running = false;
    }
    private void invalidOption() {
    }



}