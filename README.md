# Expense Reimbursement System

A full-stack web application for automated expense reimbursement management with streamlined employee claim management and approval workflow.

## Tech Stack

- **Backend**: Spring Boot (Java)
- **Frontend**: React.js
- **Database**: H2 (in-memory)
- **Styling**: Custom CSS with responsive design

## Features

- ✅ Add new expense claims
- ✅ View all expenses with filtering
- ✅ Approve/Reject expense claims
- ✅ Responsive design
- ✅ Professional UI with modern styling

## Quick Start

### Backend (Port 8080)
```bash
cd springapp
./mvnw spring-boot:run
```

### Frontend (Port 3000)
```bash
cd reactapp
npm install
npm start
```

## API Endpoints

- `POST /api/expenses` - Create new expense
- `GET /api/expenses` - Get all expenses  
- `PUT /api/expenses/{id}/status` - Update expense status

## Project Structure

```
├── springapp/          # Spring Boot backend
│   ├── src/main/java/com/example/springapp/
│   │   ├── model/      # Entity classes
│   │   ├── repository/ # JPA repositories
│   │   └── controller/ # REST controllers
└── reactapp/           # React frontend
    └── src/
        ├── App.js      # Main app component
        ├── ExpenseForm.js    # Add expense form
        ├── ExpenseList.js    # Expense list table
        └── App.css     # Styling
```