package projects.finalproject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final String libraryName;

    Library() {
        this.libraryName = "Central";
    }

    String getLibraryName() {
        return libraryName;
    }

    void printStudentInfo(Student student) {
        System.out.println("\nStudent username: " + student.getUserName());
        System.out.println("Student password: " + student.getPassword());
        System.out.println("Student major: " + student.getMajor());
        System.out.println("Student membership date: " + student.getMembershipDate());
    }

    void printBookInfo(Book book) {
        if (book == null) {
            return;
        }
        System.out.println("\nBook title: " + book.getTitle());
        System.out.println("Book author: " + book.getAuthor());
        System.out.println("Book pages: " + book.getPages());
        System.out.println("Book published year: " + book.getPublishedYear());
        if (book.getIsLoaned()) {
            System.out.println("This book is loaned at the moment ");
        } else
            System.out.println("This book is not loaned at the moment ");
        System.out.println("___________________________");
    }

    void printLoanBooksInfo(List<Loan> loans) {
        if (loans == null) {
            System.out.println("No books found");
            return;
        }

        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                printBookInfo(loan.getBook());
            }
        }
    }

    Book searchBookToChange(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return null;
        }

        Set<String> bookTitles = new HashSet<>();
        for (Book book : books) {
            bookTitles.add(book.getTitle().toLowerCase());
        }

        System.out.println("\nEnter the book title to search (or 'exit' to cancel): ");

        while (true) {
            String bookTitle = scanner.nextLine().trim();

            if (bookTitle.equalsIgnoreCase("exit")) {
                return null;
            }

            if (bookTitles.contains(bookTitle.toLowerCase())) {
                for (Book book : books) {
                    if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                        return book;
                    }
                }
            }

            System.out.println("\nThere is not a book with title " + bookTitle + "\nPlease try again");
        }
    }

    void searchBooksByPublishedYear(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        Map<Integer, List<Book>> yearMap = new HashMap<>();
        for (Book book : books) {
            int year = book.getPublishedYear();
            if (!yearMap.containsKey(year)) {
                yearMap.put(year, new ArrayList<>());
            }
            yearMap.get(year).add(book);
        }

        System.out.println("\nEnter a book published year to search (or 0 to exit): ");

        while (true) {
            try {
                int year = scanner.nextInt();
                scanner.nextLine();

                if (year == 0) {
                    System.out.println("Exiting search...");
                    break;
                }

                List<Book> foundBooks = yearMap.getOrDefault(year, new ArrayList<>());

                if (foundBooks.isEmpty()) {
                    System.out.println("\nNo books found published in " + year);
                } else {
                    System.out.println("\nFound " + foundBooks.size() + " book(s) published in " + year + ":");
                    for (Book book : foundBooks) {
                        printBookInfo(book);
                    }
                }

                System.out.println("\nEnter another year to search (or 0 to exit): ");

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid year number.");
                scanner.nextLine();
            }
        }
    }

    void searchBooksByAuthor(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        Map<String, List<Book>> authorMap = new HashMap<>();
        for (Book book : books) {
            String authorKey = book.getAuthor().toLowerCase();
            if (!authorMap.containsKey(authorKey)) {
                authorMap.put(authorKey, new ArrayList<>());
            }
            authorMap.get(authorKey).add(book);
        }

        System.out.println("\nEnter an author name to search (or 'exit' to exit): ");

        while (true) {
            String author = scanner.nextLine().trim();

            if (author.equalsIgnoreCase("exit")) {
                System.out.println("Exiting search...");
                break;
            }

            List<Book> foundBooks = authorMap.getOrDefault(author.toLowerCase(), new ArrayList<>());

            if (foundBooks.isEmpty()) {
                System.out.println("\nNo books found by author '" + author + "'");
            } else {
                System.out.println("\nFound " + foundBooks.size() + " book(s) by '" + author + "':");
                for (Book book : foundBooks) {
                    printBookInfo(book);
                }
            }

            System.out.println("\nEnter another author to search (or 'exit' to exit): ");
        }
    }

    void searchBooksByTitle(ArrayList<Book> books, Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("\nThere is no book in the database");
            return;
        }

        Map<String, List<Book>> titleMap = new HashMap<>();
        for (Book book : books) {
            String titleKey = book.getTitle().toLowerCase();
            if (!titleMap.containsKey(titleKey)) {
                titleMap.put(titleKey, new ArrayList<>());
            }
            titleMap.get(titleKey).add(book);
        }

        System.out.println("\nEnter a book title to search (or 'exit' to exit): ");

        while (true) {
            String title = scanner.nextLine().trim();

            if (title.equalsIgnoreCase("exit")) {
                System.out.println("Exiting search...");
                break;
            }

            List<Book> foundBooks = titleMap.getOrDefault(title.toLowerCase(), new ArrayList<>());

            if (foundBooks.isEmpty()) {
                System.out.println("\nNo book found with the title '" + title + "'");
            } else {
                System.out.println("\nFound " + foundBooks.size() + " book(s) with the title '" + title + "':");
                for (Book book : foundBooks) {
                    printBookInfo(book);
                }
            }

            System.out.println("\nEnter another book title to search (or 'exit' to exit): ");
        }
    }

    void printAllBooks(ArrayList<Book> books) {

        if (books.isEmpty()) {
            System.out.println("\nThere is no books in Central Library");
            return;
        }

        for (Book book : books) {
            System.out.println("\nBook title: " + book.getTitle());
            System.out.println("Book author: " + book.getAuthor());
            System.out.println("Book published year: " + book.getPublishedYear());
            System.out.println("Book pages: " + book.getPages());
            System.out.println("___________________________");
        }
    }

    void printLibrarianInfo(ArrayList<Librarian> librarians, ArrayList<Book> books, Scanner scanner) {

        if (librarians.isEmpty() || books.isEmpty()) {
            System.out.println("There is no librarian or books in library");
            return;
        }

        System.out.println("Enter a username to see librarian information about ");

        while (true) {

            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("Exiting search...");
                return;
            }

            for (Librarian librarian : librarians) {
                if (librarian.getUserName().equalsIgnoreCase(username)) {
                    System.out.println("\nLibrarian name: " + librarian.getUserName());
                    System.out.println("Librarian password: " + librarian.getPassword());

                    for (Book book : books) {
                        if (book.getTheLibrarian().equalsIgnoreCase(librarian.getUserName())) {
                            System.out.println("Added Book : " + book.getTitle());
                        }

                    }

                    System.out.println("Given books: " + librarian.getGivenCount());
                    System.out.println("Received books: " + librarian.getReceivedCount());
                } else
                    System.out.println("\nInvalid username. Please enter a valid username");
            }

            System.out.println("\nEnter another username to see librarian information about (or 'exit' to exit): ");
        }


    }

    void printStudentLoanInfo(ArrayList<Loan> loans, Scanner scanner) {
        if (loans == null || loans.isEmpty()) {
            System.out.println("There are no loans in the library");
            return;
        }

        System.out.println("Enter a username to see student's loan information (or type 'exit' to cancel):");

        while (true) {
            String username = scanner.nextLine().trim();

            if (username.equalsIgnoreCase("exit")) return;
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty! Please try again:");
                continue;
            }

            List<Loan> studentLoans = loans.stream().filter(loan -> loan.getStudent() != null).filter(loan -> username.equalsIgnoreCase(loan.getStudent().getUserName())).collect(Collectors.toList());

            if (studentLoans.isEmpty()) {
                System.out.println("No loans found for student: " + username);
                System.out.println("Please try another username or type 'exit' to cancel:");
                continue;
            }

            long totalLoans = studentLoans.size();
            long returnedCount = studentLoans.stream().filter(loan -> loan.getReturnDate() != null).count();
            long notReturnedCount = totalLoans - returnedCount;

            System.out.println("\n=== Loan Information for Student: " + username + " ===");
            System.out.println("Total loans: " + totalLoans);
            System.out.println("Returned books: " + returnedCount);
            System.out.println("Not returned books: " + notReturnedCount);

            List<Loan> notReturnedLoans = studentLoans.stream().filter(loan -> loan.getReturnDate() == null).collect(Collectors.toList());

            if (!notReturnedLoans.isEmpty()) {
                System.out.println("\nBooks not returned yet:");
                notReturnedLoans.forEach(loan ->
                        System.out.println("- " + loan.getBook().getTitle() + " (Loaned on: " + loan.getLoanDate() + ", Due: " + loan.getDueDate() + ")")
                );
            }

            System.out.println("\nEnter another username or type 'exit' to cancel:");
        }
    }

    void printLoanStatistics(ArrayList<Book> books, ArrayList<Loan> loans) {
        System.out.println("\n=== Loan Statistics Per Book ===");

        Map<String, List<Loan>> loansByBook = new HashMap<>();
        for (Loan loan : loans) {
            String bookTitle = loan.getBook().getTitle();
            if (!loansByBook.containsKey(bookTitle)) {
                loansByBook.put(bookTitle, new ArrayList<>());
            }
            loansByBook.get(bookTitle).add(loan);
        }

        for (Book book : books) {
            List<Loan> bookLoans = loansByBook.getOrDefault(book.getTitle(), new ArrayList<>());

            int returnedCount = 0;
            long totalDays = 0;

            for (Loan loan : bookLoans) {
                if (loan.getReturnDate() != null) {
                    returnedCount++;
                    totalDays += ChronoUnit.DAYS.between(loan.getLoanDate(), loan.getReturnDate());
                }
            }

            double avgDays = returnedCount > 0 ? (double) totalDays / returnedCount : 0;

            System.out.println("\nBook: " + book.getTitle());
            System.out.println("Total Times Loaned: " + bookLoans.size());
            System.out.println("Times Returned: " + returnedCount);
            System.out.println("Currently on Loan: " + (bookLoans.size() - returnedCount));
            System.out.println("Total Loan Days: " + totalDays + " days");
            System.out.println("Average Loan Duration: " + String.format("%.2f", avgDays) + " days");
            System.out.println("----------------------------");
        }
    }

    void printTop10StudentsWithMostDelays(ArrayList<Student> students, ArrayList<Loan> loans) {
        if (students.isEmpty() || loans.isEmpty()) {
            System.out.println("No students or loans available.");
            return;
        }

        Map<String, Long> studentDelays = new HashMap<>();

        for (Loan loan : loans) {
            if (loan.getStudent() != null && loan.getReturnDate() != null) {
                LocalDate dueDate = loan.getDueDate();
                LocalDate returnDate = loan.getReturnDate();

                if (returnDate.isAfter(dueDate)) {
                    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                    String username = loan.getStudent().getUserName();

                    studentDelays.put(username, studentDelays.getOrDefault(username, 0L) + daysLate);
                }
            }
        }

        List<Map.Entry<String, Long>> delayList = new ArrayList<>(studentDelays.entrySet());

        delayList.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));

        System.out.println("\n=== Top 10 Students with Most Delay Days ===");

        if (delayList.isEmpty()) {
            System.out.println("No delays found in the system.");
            return;
        }

        int count = 0;
        for (Map.Entry<String, Long> entry : delayList) {
            if (count >= 10) break;

            String username = entry.getKey();
            Long totalDelayDays = entry.getValue();

            Student student = findStudentByUsername(students, username);
            if (student != null) {
                System.out.println((count + 1) + ". " + username + " - Major: " + student.getMajor() + " - Total Delay: " + totalDelayDays + " days");
            } else {
                System.out.println((count + 1) + ". " + username + " - Total Delay: " + totalDelayDays + " days");
            }
            count++;
        }
    }

    private Student findStudentByUsername(ArrayList<Student> students, String username) {
        for (Student student : students) {
            if (student.getUserName().equals(username)) {
                return student;
            }
        }
        return null;
    }

}
