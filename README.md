# Course App API

## Overview

The Course App API is a backend service for managing courses, students, and their enrollments. It provides endpoints to manage course details, student information, and enrollments, allowing clients to fetch courses a student is enrolled in and students enrolled in a particular course.

## Features

- Manage Courses: Add, update, delete, and retrieve course details.
- Manage Students: Add, update, delete, and retrieve student information.
- Manage Enrollments: Enroll students in courses and fetch enrollment details.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Postman

### Instructions

**Modify `application.properties` file**:
   - Modify the `application.properties` file to include the username and password for MySQL database

## API Endpoints

### Courses

- **Get all courses:**

    ```http
    GET /api/courses
    ```

- **Get a course by ID:**

    ```http
    GET /api/courses/{id}
    ```

- **Add a new course:**

    ```http
    POST /api/courses
    ```

- **Update a course:**

    ```http
    PUT /api/courses/{id}
    ```

- **Delete a course:**

    ```http
    DELETE /api/courses/{id}
    ```

### Students

- **Get all students:**

    ```http
    GET /api/students
    ```

- **Get a student by ID:**

    ```http
    GET /api/students/{id}
    ```

- **Add a new student:**

    ```http
    POST /api/students
    ```

- **Update a student:**

    ```http
    PUT /api/students/{id}
    ```

- **Delete a student:**

    ```http
    DELETE /api/students/{id}
    ```

### Enrollments

- **Get all courses a student is enrolled in:**

    ```http
    GET /api/student/{id}/courses
    ```

- **Get all students enrolled in a course:**

    ```http
    GET /api/course/{id}/students
    ```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.
