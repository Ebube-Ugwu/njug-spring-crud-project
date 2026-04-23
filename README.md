# Employee CRUD API Project (Naija JUG Bootcamp - Cohort 1)

A Spring Boot REST API for managing employees with support for:

- Validation
- Pagination & filtering
- Soft & hard deletes
- Excel import
- PDF export

---

## 🚀 Tech Stack

- Java 21+
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database (Development)
- MapStruct
- Gradle

---

## ▶️ Running the Project

### 1. Clone the repository

git clone <your-repo-url>  
cd <your-project-folder>

---

### 2. Set Environment Variables

export DB_URL=jdbc:h2:mem:employeedb  
export DB_USERNAME=sa  
export DB_PASSWORD=

💡 Defaults are provided in `application.yaml`, so this step is optional for development.

---

### 3. Build the project

./gradlew build

---

### 4. Run the application

./gradlew bootRun

---

### 5. Application runs on

http://localhost:7000

---

### 6. H2 Console (Development)

http://localhost:7000/h2-console

Use:

JDBC URL: jdbc:h2:mem:testdb  
Username: sa  
Password: (leave blank)

---

## 📌 API Endpoints

Base URL:

/api/v1/employees

---

### ➕ Create Employee

POST /api/v1/employees

**Description:** Create a new employee

**Request Body:**
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "salary": 75000,
  "dateOfJoining": "2023-01-01",
  "active": true
}

**Response:**
- 201 Created

---

### 📄 Get All Employees (Paginated)

GET /api/v1/employees

**Query Parameters:**

- page → Page number  
- size → Page size  
- sort → Sorting field  
- department → Filter by department  
- active → Filter by active status  

**Response:**
- 200 OK

---

### 🔍 Get Employee by ID

GET /api/v1/employees/{id}

**Response:**
- 200 OK  
- 404 Not Found  

---

### ✏️ Update Employee (Full)

PUT /api/v1/employees/{id}

**Description:**
- Full update of employee  
- Validates request DTO  
- Checks for duplicate email  

**Response:**
- 200 OK

---

### 🩹 Update Employee (Partial)

PATCH /api/v1/employees/{id}

**Description:**
- Partial update  
- Updates only provided fields  

**Response:**
- 200 OK

---

### ❌ Soft Delete Employee

DELETE /api/v1/employees/{id}

**Description:**
- Sets active = false  

**Response:**
- 204 No Content

---

### 💀 Hard Delete Employee

DELETE /api/v1/employees/{id}/hard

**Description:**
- Permanently deletes employee  
- Only allowed if employee is already inactive  

**Response:**
- 204 No Content

---

### 💰 Filter by Salary Range

GET /api/v1/employees/salary-range?min=50000&max=100000

**Response:**
- 200 OK

---

### 📊 Import Employees (Excel)

POST /api/v1/employees/import

**Description:**
- Upload Excel file to bulk create employees  

**Content-Type:**
multipart/form-data

---

### 📄 Export Employees (PDF)

GET /api/v1/employees/export/pdf

**Description:**
- Export employee list as PDF  

---

## ⚠️ Error Handling

The API uses a global exception handler with structured responses.


## 🧠 Features

- DTO validation with @Valid and @Validated  
- Global exception handling with @RestControllerAdvice  
- MapStruct for clean DTO ↔ Entity mapping  
- Transaction management with @Transactional  
- Pagination and filtering support  
- Soft delete (active = false)  
- Hard delete protection  
- Excel import support  
- PDF export functionality  

---

## 📦 Build

./gradlew build

---

## 🧪 Run Tests

./gradlew test

---

## 👨‍💻 Author

Ebube Ugwu