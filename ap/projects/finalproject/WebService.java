package projects.finalproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Lightweight HTTP API over the library data, served inside the same
 * process as the console application. Uses the JDK built-in HttpServer.
 *
 * GET  /books            all books
 * GET  /students         all students
 * GET  /loans            all loans
 * GET  /loan-requests    all loan requests
 * POST /books            add a book            {"title","author","pages","publishedYear","librarian"}
 * POST /loan-requests    create loan request   {"bookTitle","studentPassword","durationDays"}
 * POST /loans/return     return a book         {"bookTitle","studentPassword"}
 */
class WebService {
    private static final int[] CANDIDATE_PORTS = {8080, 8090, 8091};

    private final Database db;
    private final ArrayList<Student> students;
    private final ArrayList<Librarian> librarians;
    private final ArrayList<Book> books;
    private final ArrayList<LoanRequest> loanRequests;
    private final ArrayList<Loan> loans;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .create();
    private HttpServer server;
    private int port = -1;

    WebService(Database db, ArrayList<Student> students, ArrayList<Librarian> librarians,
               ArrayList<Book> books, ArrayList<LoanRequest> loanRequests, ArrayList<Loan> loans) {
        this.db = db;
        this.students = students;
        this.librarians = librarians;
        this.books = books;
        this.loanRequests = loanRequests;
        this.loans = loans;
    }

    void start() {
        for (int candidate : CANDIDATE_PORTS) {
            try {
                server = HttpServer.create(new InetSocketAddress(candidate), 0);
                port = candidate;
                break;
            } catch (IOException e) {
                System.out.println("Port " + candidate + " is busy, trying next...");
            }
        }

        if (server == null) {
            System.out.println("Web service could not start: ports 8080, 8090 and 8091 are all busy");
            return;
        }

        server.createContext("/books", this::handleBooks);
        server.createContext("/students", this::handleStudents);
        server.createContext("/loans", this::handleLoans);
        server.createContext("/loan-requests", this::handleLoanRequests);
        server.setExecutor(null);
        server.start();
        System.out.println("Web service started on http://localhost:" + port);
    }

    void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private void handleBooks(HttpExchange exchange) throws IOException {
        try {
            if (isGet(exchange)) {
                sendJson(exchange, 200, gson.toJson(books));

            } else if (isPost(exchange)) {
                AddBookRequest body = readBody(exchange, AddBookRequest.class);

                if (body == null || isBlank(body.title) || isBlank(body.author)) {
                    sendError(exchange, 400, "Title and author are required");
                } else if (body.pages <= 0) {
                    sendError(exchange, 400, "Pages must be a positive number");
                } else if (body.publishedYear < 1000 || body.publishedYear > LocalDate.now().getYear()) {
                    sendError(exchange, 400, "Published year must be between 1000 and " + LocalDate.now().getYear());
                } else if (findLibrarian(body.librarian) == null) {
                    sendError(exchange, 404, "Librarian not found: " + body.librarian);
                } else if (findBook(body.title) != null) {
                    sendError(exchange, 409, "A book with this title already exists");
                } else {
                    Book book = new Book(body.title.trim(), body.author.trim(), body.pages,
                            body.publishedYear, findLibrarian(body.librarian).getUserName());
                    books.add(book);
                    db.saveBooks(books);
                    sendJson(exchange, 201, gson.toJson(book));
                }

            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            sendError(exchange, 400, "Invalid request: " + e.getMessage());
        }
    }

    private void handleStudents(HttpExchange exchange) throws IOException {
        try {
            if (isGet(exchange)) {
                sendJson(exchange, 200, gson.toJson(students));
            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            sendError(exchange, 400, "Invalid request: " + e.getMessage());
        }
    }

    private void handleLoans(HttpExchange exchange) throws IOException {
        try {
            if (isGet(exchange)) {
                sendJson(exchange, 200, gson.toJson(loans));

            } else if (isPost(exchange) && exchange.getRequestURI().getPath().endsWith("/return")) {
                ReturnBookBody body = readBody(exchange, ReturnBookBody.class);

                Loan activeLoan = null;
                for (Loan loan : loans) {
                    if (loan.getReturnDate() == null
                            && loan.getBook().getTitle().equalsIgnoreCase(body.bookTitle)
                            && loan.getStudent().getPassword().equals(body.studentPassword)) {
                        activeLoan = loan;
                        break;
                    }
                }

                if (activeLoan == null) {
                    sendError(exchange, 404, "No active loan found for this book and student");
                } else {
                    activeLoan.getBook().setIsLoaned(false);
                    activeLoan.setReturnDate(LocalDate.now());

                    if (!librarians.isEmpty()) {
                        Librarian receiver = librarians.get(new Random().nextInt(librarians.size()));
                        receiver.setReceivedCount();
                    }

                    db.saveLoans(loans);
                    db.saveBooks(books);
                    db.saveLibrarians(librarians);
                    sendJson(exchange, 200, gson.toJson(activeLoan));
                }

            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            sendError(exchange, 400, "Invalid request: " + e.getMessage());
        }
    }

    private void handleLoanRequests(HttpExchange exchange) throws IOException {
        try {
            if (isGet(exchange)) {
                sendJson(exchange, 200, gson.toJson(loanRequests));

            } else if (isPost(exchange)) {
                LoanRequestBody body = readBody(exchange, LoanRequestBody.class);

                Book book = findBook(body.bookTitle);
                if (book == null) {
                    sendError(exchange, 404, "Book not found: " + body.bookTitle);
                } else if (book.getIsLoaned()) {
                    sendError(exchange, 409, "Book is currently on loan");
                } else if (body.durationDays <= 0) {
                    sendError(exchange, 400, "Duration must be a positive number of days");
                } else {
                    Student student = findStudentByPassword(body.studentPassword);
                    if (student == null) {
                        sendError(exchange, 404, "Student not found");
                    } else if (!student.isActive()) {
                        sendError(exchange, 403, "Student account is deactivated");
                    } else {
                        LoanRequest request = new LoanRequest(book, student, body.durationDays);
                        loanRequests.add(request);
                        db.saveLoanRequests(loanRequests);
                        sendJson(exchange, 201, gson.toJson(request));
                    }
                }

            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            sendError(exchange, 400, "Invalid request: " + e.getMessage());
        }
    }

    private Book findBook(String title) {
        if (isBlank(title)) {
            return null;
        }
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                return book;
            }
        }
        return null;
    }

    private Student findStudentByPassword(String password) {
        if (password == null) {
            return null;
        }
        for (Student student : students) {
            if (student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    private Librarian findLibrarian(String username) {
        if (isBlank(username)) {
            return null;
        }
        for (Librarian librarian : librarians) {
            if (librarian.getUserName().equalsIgnoreCase(username.trim())) {
                return librarian;
            }
        }
        return null;
    }

    private static boolean isGet(HttpExchange exchange) {
        return exchange.getRequestMethod().equalsIgnoreCase("GET");
    }

    private static boolean isPost(HttpExchange exchange) {
        return exchange.getRequestMethod().equalsIgnoreCase("POST");
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private <T> T readBody(HttpExchange exchange, Class<T> type) throws IOException {
        InputStream in = exchange.getRequestBody();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int read;
        while ((read = in.read(chunk)) != -1) {
            buffer.write(chunk, 0, read);
        }
        return gson.fromJson(buffer.toString("UTF-8"), type);
    }

    private void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    private void sendError(HttpExchange exchange, int status, String message) throws IOException {
        sendJson(exchange, status, "{\"error\": " + gson.toJson(message) + "}");
    }

    static class AddBookRequest {
        String title;
        String author;
        int pages;
        int publishedYear;
        String librarian;
    }

    static class LoanRequestBody {
        String bookTitle;
        String studentPassword;
        int durationDays;
    }

    static class ReturnBookBody {
        String bookTitle;
        String studentPassword;
    }
}
