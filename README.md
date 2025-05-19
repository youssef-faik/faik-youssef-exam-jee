# Banking System Application

A full-stack web application for managing a banking system, including client management, credit services, and repayment functionalities. The application consists of a Spring Boot backend and an Angular frontend.

## Project Overview

This banking system allows users to:
- Manage client information
- Process different types of credits (Personal, Real Estate, Professional)
- Track and manage loan repayments
- Authenticate users with different roles (Client, Employee, Admin)

## Technology Stack

### Backend
- Java 21
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security with JWT Authentication
- MySQL Database
- Swagger/OpenAPI for API Documentation
- Maven for dependency management

### Frontend
- Angular 19.2.0
- Bootstrap 5.3.6
- RxJS for reactive programming
- TypeScript 5.7.2

## Project Structure

```
examen-jee/
│
├── backend/                   # Spring Boot backend application
│   ├── src/                   # Source files
│   │   ├── main/
│   │   │   ├── java/          # Java source code
│   │   │   └── resources/     # Configuration files
│   │   └── test/              # Test files
│   ├── API_DOCUMENTATION.md   # API documentation
│   ├── pom.xml                # Maven dependencies
│   └── run.bat                # Windows script to run backend
│
└── frontend/                  # Angular frontend application
    ├── src/                   # Source files
    │   ├── app/               # Angular components
    │   │   ├── components/    # UI components
    │   │   └── services/      # API services
    │   ├── index.html         # Main HTML file
    │   └── main.ts            # Entry point
    ├── angular.json           # Angular configuration
    └── package.json           # NPM dependencies
```

## Prerequisites

- Java Development Kit (JDK) 21
- Node.js and npm
- MySQL Server
- Angular CLI

## Getting Started

### Database Setup

1. Install and start MySQL Server
2. The application will automatically create the database if it doesn't exist (configured in `application.properties`)

### Backend Setup

1. Navigate to the backend directory:
   ```
   cd examen-jee/backend
   ```

2. Run the application using the provided batch file (Windows):
   ```
   run.bat
   ```

   Or using Maven directly:
   ```
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

3. The backend will be available at `http://localhost:8081`
4. API documentation (Swagger UI) will be available at `http://localhost:8081/swagger-ui/index.html`

### Frontend Setup

1. Navigate to the frontend directory:
   ```
   cd examen-jee/frontend
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm start
   ```

4. The frontend will be available at `http://localhost:4200`

## API Documentation

The API documentation is available via Swagger UI when the application is running. You can access it at:
```
http://localhost:8081/swagger-ui/index.html
```

For more details, see the [API Documentation](backend/API_DOCUMENTATION.md) file.

## Authentication

The application uses JWT (JSON Web Token) for authentication with the following roles:
- **ROLE_CLIENT**: Regular bank clients
- **ROLE_EMPLOYE**: Bank employees who can manage clients and credits
- **ROLE_ADMIN**: Administrators with full access to all operations

### Authentication Endpoints

- **User Registration**: `POST /api/v1/auth/signup`
- **User Login**: `POST /api/v1/auth/signin`

## Main Features

### Client Management
- Create, read, update, and delete client information
- View client details and associated credits

### Credit Management
- Support for different credit types (Personal, Real Estate, Professional)
- Track credit status (PENDING, APPROVED, REJECTED)
- Associate credits with clients

### Repayment Management
- Track repayments for credits
- Support for different payment types

## Development

### Backend Development

The backend uses a standard Spring Boot structure with the following packages:
- `controller`: REST API endpoints
- `service`: Business logic
- `repository`: Data access layer
- `entity`: JPA entities
- `dto`: Data Transfer Objects
- `security`: Authentication and authorization
- `config`: Configuration classes
- `exception`: Exception handling

### Frontend Development

The Angular frontend follows a modular structure with:
- Components for different parts of the application (clients, credits, repayments)
- Services for API communication
- Angular routing for navigation

## License

This project is for educational purposes only.

## Contributors

- Faikyoussef - Project Developer
