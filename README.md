# Library Management System

## Introduction
The Library Management System is a web-based application designed to manage books, patrons, and borrowing records in a library. It provides functionalities to add, update, delete, and retrieve books and patrons, as well as borrow and return books.

## Technologies Used
- Java
- Spring Boot
- MySQL
- Hibernate
- Maven
- RESTful API
- Lombok

## Features
- **Logging**: Method calls, cache status, exceptions, and method execution time are logged using SLF4J and Logback. Logs are saved in `resources/application.log`.
- **Caching**: Caching is implemented using Spring's `@Cacheable` annotation for retrieving patrons by ID and books by ID to improve performance.

## Setup Instructions
1. **Clone the Repository**: Clone the repository from GitHub to your local machine.
git clone https://github.com/HassanMahmoud22/LibraryManagementSystem.git

2. **Database Setup**:
- Install MySQL if not already installed.
- Create a MySQL database named `library_system`.
- Update the `application.properties` file with your MySQL database credentials.

3. **Build and Run**:
- Navigate to the project directory.
- Build the project using Maven.
  ```
  mvn clean install
  ```
- Run the application.
  ```
  mvn spring-boot:run
  ```

4. **Accessing the Application**:
- Once the application is running, you can access it at `http://localhost:8080`.

## API Endpoints
Below are the endpoints available in the Library Management System:

### Book Endpoints
1. **GET /api/books**: Retrieve all books.
    - **URL**: `http://localhost:8080/api/books`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

2. **GET /api/books/{id}**: Retrieve a book by ID.
    - **URL**: `http://localhost:8080/api/books/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

3. **POST /api/books**: Add a new book.
    - **URL**: `http://localhost:8080/api/books`
    - **Headers**: `X-ADMIN-KEY: ADMIN`
    - **Request Body**: JSON representing the book to be added.
        ```json
        {
            "title": "Example Book Title",
            "author": "John Doe",
            "publicationYear": 2022,
            "isbn": "123-4567890123"
        }
        ```

4. **PUT /api/books/{id}**: Update an existing book.
    - **URL**: `http://localhost:8080/api/books/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`
    - **Request Body**: JSON representing the updated book details.
        ```json
        {
            "title": "Updated Book Title",
            "author": "Jane Smith",
            "publicationYear": 2023,
            "isbn": "123-4567890456"
        }
        ```

5. **DELETE /api/books/{id}**: Delete a book by ID.
    - **URL**: `http://localhost:8080/api/books/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

### Patron Endpoints
1. **GET /api/patrons**: Retrieve all patrons.
    - **URL**: `http://localhost:8080/api/patrons`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

2. **GET /api/patrons/{id}**: Retrieve a patron by ID.
    - **URL**: `http://localhost:8080/api/patrons/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

3. **POST /api/patrons**: Add a new patron.
    - **URL**: `http://localhost:8080/api/patrons`
    - **Headers**: `X-ADMIN-KEY: ADMIN`
    - **Request Body**: JSON representing the patron to be added.
        ```json
        {
            "name": "John Doe",
            "phoneNumber": "1234567890",
            "emailAddress": "john.doe@example.com"
        }
        ```

4. **PUT /api/patrons/{id}**: Update an existing patron.
    - **URL**: `http://localhost:8080/api/patrons/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`
    - **Request Body**: JSON representing the updated patron details.
        ```json
        {
            "name": "Jane Smith",
            "phoneNumber": "9876543210",
            "emailAddress": "jane.smith@example.com"
        }
        ```

5. **DELETE /api/patrons/{id}**: Delete a patron by ID.
    - **URL**: `http://localhost:8080/api/patrons/{id}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

### Borrowing Record Endpoints
1. **POST /api/borrow/{bookId}/patron/{patronId}**: Borrow a book by a patron.
    - **URL**: `http://localhost:8080/api/borrow/{bookId}/patron/{patronId}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

2. **PUT /api/borrow/{bookId}/patron/{patronId}**: Return a borrowed book by a patron.
    - **URL**: `http://localhost:8080/api/borrow/{bookId}/patron/{patronId}`
    - **Headers**: `X-ADMIN-KEY: ADMIN`

## Authentication
- All requests must include the `X-ADMIN-KEY` header with the value `ADMIN` for authentication purposes.

## Request and Response Payloads
- For adding and updating books and patrons, use the provided DTO (Data Transfer Object) classes (`BookDTORequest`, `PatronDTORequest`) as request payloads.
- The response payloads for book-related endpoints are represented by the `BookDTOResponse` class.
- Ensure that request payloads adhere to the specified validation constraints for each field.

## Error Handling
- The application provides error responses with appropriate HTTP status codes and error messages for invalid requests or server errors.

## Testing
- Unit tests are provided for controller and service classes to ensure the correctness of business logic and endpoint functionalities.

## Additional Notes
- Ensure that MySQL is running and accessible before starting the application.
- Customize the database configuration and authentication mechanism as per your requirements.
- Update the port number in the `application.properties` file if required.

This documentation should guide you through setting up and using the Library Management System application effectively. If you have any further questions or issues, feel free to reach out for assistance.


