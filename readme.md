## \----Benjamin Onyedika Udegbunam--------







# Spring Security Multi‑Module Project

A production-style **multi-module Spring Boot security project** implementing JWT authentication, role-based authorization, and reusable security core.

\---

# 📦 Project Structure

```
spring-security-project
│
├── security-core        → Reusable security module (JWT, filters, config)
├── sample-app           → Demo application using security-core
└── pom.xml              → Parent multi-module Maven config
```

\---

# 🧩 Modules

## 1\. security-core

Reusable Spring Security module containing:

* JWT Token provider
* Authentication filter
* Security configuration
* UserDetailsService implementation
* Password encoding
* Role-based authorization
* Exception handling

This module **does not run standalone**.

It is imported by other services.

\---

## 2\. sample-app

Demo application that uses `security-core`

Contains:

* User entity
* Role entity
* Auth controller
* Protected endpoints
* Integration tests
* H2 database configuration

\---

# ⚙️ Tech Stack

* Java 17
* Spring Boot 3
* Spring Security 6
* JWT (jjwt)
* Spring Data JPA
* H2 Database
* MockMvc Integration Tests
* Maven Multi‑Module

\---

# ▶️ How To Build

From project root:

```
mvn clean install
```

This will build:

* security-core
* sample-app

\---

# ▶️ How To Run

Run the sample application:

```
cd sample-app
mvn spring-boot:run
```

Or run main class:

```
SampleAppApplication.java
```

Application starts on:

```
http://localhost:8080
```

\---

# 🔐 Authentication Flow

```
Register User → Login → Receive JWT → Call Protected API
```

JWT must be sent in header:

```
Authorization: Bearer <token>
```

\---

# 🧪 Integration Tests

Integration tests use:

* @SpringBootTest
* MockMvc
* H2 in‑memory DB

Run tests:

```
mvn test
```

Tests cover:

* User registration
* Login
* JWT generation
* Authorized endpoint access
* Role-based access

\---

# 🗄 Database

H2 in-memory database is used.

Console:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:mem:testdb
```

User:

```
sa
```

Password:

```
(empty)
```

\---

# 🧠 Design Decisions

### Multi‑Module Architecture

`security-core` is reusable across microservices.

Benefits:

* No duplicated security config
* Plug \& play security
* Centralized JWT logic

\---

### JWT Authentication

Stateless authentication used instead of sessions.

Benefits:

* Scalable
* Microservice friendly
* No server-side session storage

\---

### Password Encoding

Passwords encoded using:

```
BCryptPasswordEncoder
```

Passwords are never stored in plain text.

\---

# 🧪 Test Coverage

Integration tests validate:

* Register endpoint
* Login endpoint
* JWT authentication
* Role authorization
* Security filter chain

\---

# 📁 Example Postman Collection

Collection includes:

* Register User
* Register Admin
* Login
* User Endpoint
* Admin Endpoint

Import the collection.json file attached in the root directory:

```
```
# 🚀 Future Improvements

* Refresh tokens
* Logout endpoint
* Token blacklist
* Role management API
* User management API
* Database persistence (Postgres)
* Docker support

# 🏁 Done

