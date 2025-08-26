# Expense Reimbursement System Setup Instructions

## Overview
This is a complete Web-Based Automated Expense Reimbursement System with React.js frontend and Spring Boot backend.

## Tech Stack
- **Frontend**: React.js (Port 8081)
- **Backend**: Spring Boot (Port 8080)
- **Database**: H2 In-Memory Database

## Features
- ✅ Employee expense submission form with validation
- ✅ Complete expense list with filtering capabilities
- ✅ Finance team approval/rejection workflow
- ✅ Real-time status updates
- ✅ Responsive design
- ✅ Error handling and loading states

## Running the Application

### Backend (Spring Boot)
1. Open terminal and navigate to the springapp directory:
   ```bash
   cd springapp
   ```

2. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on **http://localhost:8080**

### Frontend (React.js)
1. Open a new terminal and navigate to the reactapp directory:
   ```bash
   cd reactapp
   ```

2. Install dependencies:
   ```bash
   npm i
   ```

3. Start the React application:
   ```bash
   npm start
   ```

The frontend will start on **http://localhost:8081**

## API Endpoints

### 1. Create Expense
- **POST** `/api/expenses`
- Creates a new expense claim with validation

### 2. Get All Expenses
- **GET** `/api/expenses`
- Retrieves all expense claims

### 3. Update Expense Status
- **PUT** `/api/expenses/{id}/status`
- Updates expense status (APPROVED/REJECTED) with remarks

## Application Workflow

1. **Employee submits expense**: Fill out form with Employee ID, Amount, Description, and Date
2. **Expense appears in list**: New expense shows with "PENDING" status
3. **Finance team processes**: Use filter to view pending expenses and approve/reject
4. **Status updates**: Real-time updates reflect in the expense list

## Database
The application uses H2 in-memory database. You can access the H2 console at:
**http://localhost:8080/h2-console**

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Testing
Run test cases using:
```bash
# Backend tests
cd springapp
mvn test

# Frontend tests
cd reactapp
npm test
```

## Troubleshooting
- If you encounter port conflicts, ensure ports 8080 and 8081 are available
- If node_modules issues occur, delete the folder and run `npm i` again
- Ensure Java 17+ is installed for the Spring Boot application