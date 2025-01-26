# library-management

The Library Management System is a RESTful application designed for managing a library. It allows the administration of books, authors, publishers, users, and their borrowing activities. The system also supports overdue detection and fine management.

---

## Table of Contents

1. [Features](#features)
2. [Technologies](#technologies)
3. [Usage](#usage)
4. [API Documentation](#api-documentation)
5. [Database Structure](#database-structure)
6. [Planned Extensions](#planned-extensions)
7. [Contributing](#contributing)
8. [License](#license)

---

## Features

- **Book Management**:
    - Add, update, and delete books.
    - Track book statuses (available, borrowed).
- **Author and Publisher Management**:
    - Add, update, and delete authors and publishers.
    - Associate books with authors and publishers.
- **User Management**:
    - Register and manage library users.
- **Borrowing Management**:
    - Allow users to borrow and return books.
    - Automatically detect overdue books and generate fines.
- **Reports and Statistics**:
    - Generate reports on popular books, overdue records, and paid fines.

---

## Technologies

- **Backend**: Java, Spring Boot
- **Database**: PostgreSQL
- **Testing**: Groovy Spock
- **Build Tool**: Gradle
- **Documentation**: Spring REST Docs
- **Docker**:
    - Support for Docker containers for easy deployment.

---

## Usage

- **REST API Documentation**:
    - The API documentation is generated using **Spring REST Docs** as part of the test suite.
    - To generate the documentation:
        1. Run the tests:
           ```bash
           ./gradlew test
           ```
        2. The generated documentation will be available in the `build/doc-api-adoc` directory.
        3. These snippets can be integrated into a static site or shared as-is for API consumers.

- **Sample API Request (curl)**:
    - Add a new book:
      ```bash
      curl -X POST http://localhost:8080/books \
      -H "Content-Type: application/json" \
      -d '{
        "title": "The Great Gatsby",
        "authorId": 1,
        "publisherId": 2,
        "status": "available"
      }'
      ```

---

## API Documentation

The API is documented using **Spring REST Docs**, which generates static documentation based on integration tests.

### Endpoints

| Method | Endpoint            | Description                                                                                                                                                                                 |
|--------|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | `/api/books`        | Retrieve a list of all books.                                                                                                                                                               |
| POST   | `/api/books`        | Create a new book.                                                                                                                                                                          |
| GET    | `/api/books/{id}`   | Get details of a specific book.                                                                                                                                                             |
| PUT    | `/api/books/{id}`   | Update a book. Automatically changes the book's status to "BORROWED" and creates a `Borrow` entity if borrowed. Also, creates a new `Author` entity if the specified author does not exist. |
| DELETE | `/api/books/{id}`   | Delete a book.                                                                                                                                                                              |
| GET    | `/api/authors`      | Retrieve a list of all authors.                                                                                                                                                             |
| POST   | `/api/authors`      | Create a new author.                                                                                                                                                                        |
| GET    | `/api/authors/{id}` | Get details of a specific author.                                                                                                                                                           |
| GET    | `/api/users`        | Retrieve a list of users.                                                                                                                                                                   |
| POST   | `/api/borrows`      | Borrow a book.                                                                                                                                                                              |
| PUT    | `/api/borrows/{id}` | Update a borrow. Creates a `Fine` entity if the book is returned after its `dueDate`.                                                                                                       |
| GET    | `/api/borrows/{id}` | Return a borrowed book.                                                                                                                                                                     |
| GET    | `/api/fines`        | Retrieve a list of all fines.                                                                                                                                                               |

---

## Database Structure

### Tables

- **`author`**: Stores information about authors.
- **`publisher`**: Stores information about publishers.
- **`book`**: Stores information about books and their statuses.
- **`user`**: Stores information about library users.
- **`borrow`**: Stores information about borrow records.
- **`fine`**: Stores information about fines for overdue borrows.

---

## Planned Extensions

- **Search Functionality**:
    - Enable full-text search for books by title, author, or keywords.
- **Notifications**:
    - Notify users about upcoming return deadlines.
- **Multilingual Support**:
    - Add support for multiple languages in the user interface.
- **Advanced Reporting**:
    - Generate statistical insights on borrowing trends and user behavior.

---

## Contributing

If you would like to contribute to this project, feel free to submit a **pull request** or report issues in the **issues** section.

---

## License

This project is licensed under the [MIT License](LICENSE).
