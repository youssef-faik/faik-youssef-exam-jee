# API Documentation

## Swagger / OpenAPI Integration

The project includes comprehensive API documentation using Swagger/OpenAPI, which provides:

- Interactive documentation of all API endpoints
- The ability to test endpoints directly from the browser
- Examples and schema definitions for all request and response bodies
- Details about path parameters, query parameters, and response codes

## Accessing Swagger UI

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8081/swagger-ui/index.html
```

### Verifying the Swagger UI

To verify that the Swagger UI is correctly displaying all endpoints:

1. Start the application by running the `run.bat` script or using the Maven wrapper:
   ```
   .\mvnw.cmd spring-boot:run
   ```

2. Open a web browser and navigate to:
   ```
   http://localhost:8081/swagger-ui/index.html
   ```

3. Verify that:
   - All controller endpoints are visible and grouped properly
   - Request schemas show proper descriptions and examples
   - Response schemas are documented
   - Each endpoint has appropriate operations (GET, POST, PUT, DELETE)
   - The API documentation contains accurate examples that match the DTO structure

4. Test endpoints directly using the "Try it out" button to ensure they are working correctly

## Systematic Verification of API Documentation

To ensure the Swagger UI is properly configured and displays all endpoints correctly, follow these verification steps:

1. **Verify Tag Groups**: Confirm that the three main controller groups are displayed:
   - Client Management
   - Credit Management
   - Remboursement Management

2. **Verify Endpoint Documentation**:
   - Expand each endpoint to check that descriptions are meaningful
   - Verify that path parameters are correctly documented
   - Check that request body schemas include appropriate examples
   - Ensure response codes are documented (200, 201, 400, 404, etc.)

3. **Verify DTOs and Schemas**:
   - In the Schemas section at the bottom of the page, check:
     - ClientDto shows fields: id, name, email with proper descriptions
     - CreditDto shows all fields including type-specific ones (motif, bienType, raisonSociale)
     - RemboursementDto shows fields: id, amount, date, type, creditId

4. **Test Sample API Calls**:
   - Create a client using the POST endpoint
   - Retrieve the client to verify it was created
   - Create different types of credits (Personal, Real Estate, Professional)
   - Create repayments for a credit
   - Test update and delete operations as needed

Note: The Swagger UI also provides an alternative JSON format at `/v3/api-docs` which can be used by other documentation tools.

## Troubleshooting

If you encounter any issues while running the application or accessing the Swagger UI, consider these solutions:

### Running the Application

1. If the `run.bat` script fails, try running the Maven wrapper commands directly in the terminal:
   ```
   cd C:\Users\youss\IdeaProjects\examen-jee\backend
   .\mvnw.cmd clean package
   .\mvnw.cmd spring-boot:run
   ```

2. Check the application.properties file to ensure the server port is correctly set to 8081:
   ```
   server.port=8081
   ```

3. Verify that no other applications are using port 8081 (if needed, you can change the port in application.properties)

### Swagger UI Access Issues

1. If you can't access Swagger UI at http://localhost:8081/swagger-ui/index.html, try:
   - http://localhost:8081/swagger-ui.html (older SpringDoc versions)
   - http://localhost:8081/v3/api-docs (for the raw OpenAPI JSON)

2. Ensure the springdoc-openapi dependency is correctly added in pom.xml:
   ```xml
   <dependency>
       <groupId>org.springdoc</groupId>
       <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
       <version>2.3.0</version>
   </dependency>
   ```

3. Check the application logs for any errors related to SpringDoc or OpenAPI configuration

## API Endpoints Summary

### Client Management Endpoints
| Method | Endpoint                | Description                   | Status Codes          |
|--------|-------------------------|-------------------------------|----------------------|
| GET    | /api/v1/clients         | Get all clients               | 200                  |
| GET    | /api/v1/clients/{id}    | Get a specific client by ID   | 200, 404            |
| POST   | /api/v1/clients         | Create a new client           | 201, 400            |
| PUT    | /api/v1/clients/{id}    | Update an existing client     | 200, 400, 404       |
| DELETE | /api/v1/clients/{id}    | Delete a client               | 204, 404            |

### Credit Management Endpoints
| Method | Endpoint                               | Description                          | Status Codes          |
|--------|----------------------------------------|--------------------------------------|----------------------|
| GET    | /api/v1/credits                        | Get all credits                      | 200                  |
| GET    | /api/v1/credits?status={status}        | Filter credits by status             | 200                  |
| GET    | /api/v1/credits/{id}                   | Get a specific credit by ID          | 200, 404            |
| GET    | /api/v1/credits/clients/{clientId}     | Get all credits for a client         | 200, 404            |
| POST   | /api/v1/credits/clients/{clientId}     | Create a new credit for a client     | 201, 400, 404       |
| PUT    | /api/v1/credits/{id}                   | Update an existing credit            | 200, 400, 404       |
| DELETE | /api/v1/credits/{id}                   | Delete a credit                      | 204, 404            |

### Remboursement (Repayment) Management Endpoints
| Method | Endpoint                                     | Description                          | Status Codes          |
|--------|----------------------------------------------|--------------------------------------|----------------------|
| GET    | /api/v1/remboursements                       | Get all repayments                   | 200                  |
| GET    | /api/v1/remboursements/{id}                  | Get a specific repayment by ID       | 200, 404            |
| GET    | /api/v1/remboursements/credits/{creditId}    | Get all repayments for a credit      | 200, 404            |
| POST   | /api/v1/remboursements/credits/{creditId}    | Create a new repayment for a credit  | 201, 400, 404       |
| PUT    | /api/v1/remboursements/{id}                  | Update an existing repayment         | 200, 400, 404       |
| DELETE | /api/v1/remboursements/{id}                  | Delete a repayment                   | 204, 404            |

## Example API Requests and Responses

### Client Management

#### Create a Client
**Request**
```json
POST /api/v1/clients
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Update a Client
**Request**
```json
PUT /api/v1/clients/1
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

### Credit Management

#### Create a Personal Credit
**Request**
```json
POST /api/v1/credits/clients/1
{
  "amount": 10000.0,
  "status": "PENDING",
  "typeCredit": "PERSONNEL",
  "motif": "Home renovation"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "amount": 10000.0,
  "date": "2023-05-19",
  "status": "PENDING",
  "clientId": 1,
  "typeCredit": "PERSONNEL",
  "motif": "Home renovation"
}
```

#### Create a Real Estate Credit
**Request**
```json
POST /api/v1/credits/clients/1
{
  "amount": 250000.0,
  "status": "PENDING",
  "typeCredit": "IMMOBILIER",
  "bienType": "APARTMENT"
}
```

**Response (201 Created)**
```json
{
  "id": 2,
  "amount": 250000.0,
  "date": "2023-05-19",
  "status": "PENDING",
  "clientId": 1,
  "typeCredit": "IMMOBILIER",
  "bienType": "APARTMENT"
}
```

#### Create a Professional Credit
**Request**
```json
POST /api/v1/credits/clients/1
{
  "amount": 50000.0,
  "status": "PENDING",
  "typeCredit": "PROFESSIONNEL",
  "motif": "Equipment purchase",
  "raisonSociale": "ABC Corporation"
}
```

**Response (201 Created)**
```json
{
  "id": 3,
  "amount": 50000.0,
  "date": "2023-05-19",
  "status": "PENDING",
  "clientId": 1,
  "typeCredit": "PROFESSIONNEL",
  "motif": "Equipment purchase",
  "raisonSociale": "ABC Corporation"
}
```

#### Update a Credit Status
**Request**
```json
PUT /api/v1/credits/1
{
  "amount": 10000.0,
  "status": "APPROVED",
  "typeCredit": "PERSONNEL",
  "motif": "Home renovation"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "amount": 10000.0,
  "date": "2023-05-19",
  "status": "APPROVED",
  "clientId": 1,
  "typeCredit": "PERSONNEL",
  "motif": "Home renovation"
}
```

### Remboursement (Repayment) Management

#### Create a Repayment
**Request**
```json
POST /api/v1/remboursements/credits/1
{
  "amount": 500.0,
  "date": "2023-06-01",
  "type": "PAYMENT"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "amount": 500.0,
  "date": "2023-06-01",
  "type": "PAYMENT",
  "creditId": 1
}
```

#### Update a Repayment
**Request**
```json
PUT /api/v1/remboursements/1
{
  "amount": 550.0,
  "date": "2023-06-15",
  "type": "PARTIAL_PAYMENT"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "amount": 550.0,
  "date": "2023-06-15",
  "type": "PARTIAL_PAYMENT",
  "creditId": 1
}
```
