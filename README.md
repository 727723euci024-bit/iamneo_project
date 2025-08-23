# Expense Reimbursement System

A web-based automated expense reimbursement system for streamlined employee claim management and approval workflow.

## Tech Stack

- **Frontend**: React.js (running on port 8081)
- **Backend**: Spring Boot (running on port 8080)
- **Database**: H2 (in-memory database)

## Features

### Employee Features
- Submit new expense claims with validation
- View all submitted expenses
- Real-time status updates

### Finance Team Features
- View all expense claims
- Filter expenses by status (All, Pending, Approved, Rejected)
- Approve or reject pending expenses
- Add remarks when rejecting expenses

## API Endpoints

### 1. Create Expense
- **POST** `/api/expenses`
- Creates a new expense claim with validation

### 2. Get All Expenses
- **GET** `/api/expenses`
- Retrieves all expense claims

### 3. Update Expense Status
- **PUT** `/api/expenses/{id}/status`
- Updates expense status (APPROVED/REJECTED) with optional remarks

## Running the Application

### Backend (Spring Boot)
1. Navigate to the springapp directory:
   ```bash
   cd springapp
   ```
2. Start the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will run on http://localhost:8080

### Frontend (React)
1. Navigate to the reactapp directory:
   ```bash
   cd reactapp
   ```
2. Install dependencies:
   ```bash
   npm i
   ```
3. Start the application:
   ```bash
   npm start
   ```
   The frontend will run on http://localhost:8081

## Usage

1. **Add Expense**: Navigate to "Add Expense" to submit new expense claims
2. **View Expenses**: Navigate to "Expense List" to view all expenses
3. **Manage Expenses**: Finance team can approve/reject pending expenses from the list

## Validation Rules

- Amount must be greater than 0
- Description must be between 5-200 characters
- Date cannot be in the future
- Employee ID is required
- Remarks are required when rejecting expenses

## Database

The application uses H2 in-memory database. You can access the H2 console at:
http://localhost:8080/h2-console

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)