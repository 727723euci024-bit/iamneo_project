import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from 'react-router-dom';
import ExpenseForm from './ExpenseForm';
import ExpenseList from './ExpenseList';
import './App.css';

function Navigation() {
  const location = useLocation();
  
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <ul className="navbar-nav">
          <li>
            <Link 
              to="/" 
              className={`nav-link ${location.pathname === '/' ? 'active' : ''}`}
            >
              Home
            </Link>
          </li>
          <li>
            <Link 
              to="/add" 
              className={`nav-link ${location.pathname === '/add' ? 'active' : ''}`}
            >
              Add Expense
            </Link>
          </li>
          <li>
            <Link 
              to="/list" 
              className={`nav-link ${location.pathname === '/list' ? 'active' : ''}`}
            >
              Expense List 
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

function App() {
  return (
    <Router>
      <div className="app">
        <Navigation />
        <Routes>
          <Route path="/add" element={<ExpenseForm />} />
          <Route path="/list" element={<ExpenseList />} />
          <Route path="/" element={<ExpenseForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;