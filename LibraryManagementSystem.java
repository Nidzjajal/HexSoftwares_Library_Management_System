import java.time.LocalDate;
import java.util.*;

// Book Class
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isIssued;
    private LocalDate issueDate;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isIssued = false;
        this.issueDate = null;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void issueBook() {
        this.isIssued = true;
        this.issueDate = LocalDate.now();
    }

    public void returnBook() {
        this.isIssued = false;
        this.issueDate = null;
    }

    @Override
    public String toString() {
        return "Book: " + title + " by " + author + " (ISBN: " + isbn + ")" + (isIssued ? " [Issued on: " + issueDate + "]" : "");
    }
}

// Member Class
class Member {
    private String name;
    private String memberId;
    private double balance;
    private List<Book> issuedBooks;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.balance = 0.0;
        this.issuedBooks = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    public double checkBalance() {
        return balance;
    }

    public void issueBook(Book book) {
        // Check if user has deposited any amount
        if (balance <= 0) {
            System.out.println("You need to deposit some amount before issuing a book.");
            return;
        }
        if (!book.isIssued()) {
            book.issueBook();
            issuedBooks.add(book);
            System.out.println(name + " has issued " + book.getTitle() + " on " + book.getIssueDate());
        } else {
            System.out.println("Book is already issued.");
        }
    }

    public void returnBook(Book book) {
        if (issuedBooks.contains(book)) {
            book.returnBook();
            issuedBooks.remove(book);
            System.out.println(name + " has returned " + book.getTitle());
        } else {
            System.out.println("This book was not issued by " + name);
        }
    }

    public void listIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println(name + " has no issued books.");
        } else {
            for (Book book : issuedBooks) {
                System.out.println(book);
            }
        }
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Member: " + name + " (ID: " + memberId + ", Balance: " + balance + ")";
    }
}

// Library Class
class Library {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void listBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void listMembers() {
        for (Member member : members) {
            System.out.println(member);
        }
    }
}

// Main Class with Menu System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Dynamic member creation
        System.out.print("Enter your name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter your member ID: ");
        String memberId = scanner.nextLine();
        Member member = new Member(memberName, memberId);
        library.registerMember(member);

        // Adding some default books to the library
        library.addBook(new Book("Java Programming", "James Gosling", "123456"));
        library.addBook(new Book("Data Structures", "Anuradha A. Puntambekar", "789012"));
        library.addBook(new Book("C Programming", "Dennis Ritchie", "127256"));
        library.addBook(new Book("Android ", "Rich Miner", "789812"));

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. List Books");
            System.out.println("5. Add Book");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. List Issued Books");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    member.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    member.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Current Balance: " + member.checkBalance());
                    break;
                case 4:
                    library.listBooks();
                    break;
                case 5:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    library.addBook(new Book(title, author, isbn));
                    System.out.println("Book added successfully.");
                    break;
                case 6:
                    System.out.print("Enter book title to issue: ");
                    String issueTitle = scanner.nextLine();
                    boolean foundToIssue = false;
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equalsIgnoreCase(issueTitle)) {
                            member.issueBook(book);
                            foundToIssue = true;
                            break;
                        }
                    }
                    if (!foundToIssue) {
                        System.out.println("Book not found.");
                    }
                    break;
                case 7:
                    System.out.print("Enter book title to return: ");
                    String returnTitle = scanner.nextLine();
                    boolean foundToReturn = false;
                    for (Book book : member.getIssuedBooks()) {
                        if (book.getTitle().equalsIgnoreCase(returnTitle)) {
                            member.returnBook(book);
                            foundToReturn = true;
                            break;
                        }
                    }
                    if (!foundToReturn) {
                        System.out.println("This book was not issued by " + memberName);
                    }
                    break;
                case 8:
                    member.listIssuedBooks();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
