import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    int bookId;
    String title;
    String author;
    String category;
    boolean isIssued;

    public Book(int id, String t, String a, String c, boolean issued) {
        this.bookId = id;
        this.title = t;
        this.author = a;
        this.category = c;
        this.isIssued = issued;
    }

    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
    }

    public void markAsIssued() { isIssued = true; }
    public void markAsReturned() { isIssued = false; }

    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }
}

class SortByAuthor implements Comparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.author.compareToIgnoreCase(b2.author);
    }
}

class Member {
    int memberId;
    String name;
    String email;
    List<Integer> issuedBooks = new ArrayList<>();

    public Member(int id, String n, String e) {
        this.memberId = id;
        this.name = n;
        this.email = e;
    }

    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Issued Books: " + issuedBooks);
    }

    public void addIssuedBook(int id) {
        issuedBooks.add(id);
    }

    public void returnIssuedBook(int id) {
        issuedBooks.remove(Integer.valueOf(id));
    }
}

public class LibraryManager {

    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();
    Set<String> categories = new HashSet<>();

    Scanner sc = new Scanner(System.in);

    public void loadFromFile() {
        try {
            File f1 = new File("books.txt");
            File f2 = new File("members.txt");

            if (!f1.exists()) f1.createNewFile();
            if (!f2.exists()) f2.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(f1));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int id = Integer.parseInt(p[0]);
                String t = p[1];
                String a = p[2];
                String c = p[3];
                boolean issued = Boolean.parseBoolean(p[4]);
                books.put(id, new Book(id, t, a, c, issued));
                categories.add(c);
            }
            br.close();

            br = new BufferedReader(new FileReader(f2));
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int id = Integer.parseInt(p[0]);
                String n = p[1];
                String e = p[2];
                Member m = new Member(id, n, e);

                if (p.length > 3 && !p[3].equals("none")) {
                    for (String x : p[3].split(";")) {
                        m.issuedBooks.add(Integer.parseInt(x));
                    }
                }
                members.put(id, m);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"));
            for (Book b : books.values()) {
                bw.write(b.bookId + "," + b.title + "," + b.author + "," +
                         b.category + "," + b.isIssued);
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter("members.txt"));
            for (Member m : members.values()) {
                StringBuilder sb = new StringBuilder();
                for (int id : m.issuedBooks) sb.append(id).append(";");
                String issued = sb.length() == 0 ? "none" : sb.toString();

                bw.write(m.memberId + "," + m.name + "," + m.email + "," + issued);
                bw.newLine();
            }
            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Title: ");
        String t = sc.nextLine();

        System.out.print("Enter Author: ");
        String a = sc.nextLine();

        System.out.print("Enter Category: ");
        String c = sc.nextLine();

        books.put(id, new Book(id, t, a, c, false));
        categories.add(c);

        System.out.println("Book added.");
        saveToFile();
    }

    public void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String n = sc.nextLine();

        System.out.print("Enter Email: ");
        String e = sc.nextLine();

        members.put(id, new Member(id, n, e));
        System.out.println("Member added.");
        saveToFile();
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();

        if (!books.containsKey(bid)) {
            System.out.println("Book not found.");
            return;
        }
        if (!members.containsKey(mid)) {
            System.out.println("Member not found.");
            return;
        }

        Book b = books.get(bid);
        Member m = members.get(mid);

        if (b.isIssued) {
            System.out.println("Book already issued.");
            return;
        }

        b.markAsIssued();
        m.addIssuedBook(bid);

        System.out.println("Book Issued.");
        saveToFile();
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        if (!books.containsKey(bid)) {
            System.out.println("Book not found.");
            return;
        }

        Book b = books.get(bid);
        b.markAsReturned();

        for (Member m : members.values()) {
            m.returnIssuedBook(bid);
        }

        System.out.println("Book Returned.");
        saveToFile();
    }

    public void searchBooks() {
        sc.nextLine();
        System.out.print("Search (title/author/category): ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.displayBookDetails();
                System.out.println("----------------");
            }
        }
    }

    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());
        System.out.println("1. Sort by Title\n2. Sort by Author");
        int ch = sc.nextInt();

        if (ch == 1) Collections.sort(list);
        else Collections.sort(list, new SortByAuthor());

        for (Book b : list) {
            b.displayBookDetails();
            System.out.println("---------------");
        }
    }

    public void menu() {
        loadFromFile();

        int choice;
        do {
            System.out.println("\n=== City Library Digital Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7: saveToFile(); System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 7);
    }

    public static void main(String[] args) {
        new LibraryManager().menu();
    }
}
