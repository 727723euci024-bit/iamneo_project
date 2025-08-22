import React, { useState } from 'react';

function ExpenseForm() {
  const [formData, setFormData] = useState({
    employeeId: '',
    amount: '',
    description: '',
    date: ''
  });
  const [message, setMessage] = useState('');
  const [messageType, setMessageType] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.employeeId || !formData.amount || !formData.description || !formData.date) {
      setMessage('All fields are required');
      setMessageType('error');
      return;
    }
    
    if (formData.amount <= 0) {
      setMessage('Amount must be greater than 0');
      setMessageType('error');
      return;
    }
    
    if (formData.description.length < 5 || formData.description.length > 200) {
      setMessage('Description must be between 5-200 characters');
      setMessageType('error');
      return;
    }

    try {
      const response = await fetch('http://localhost:8081/api/expenses', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...formData,
          amount: parseFloat(formData.amount),
          employeeId: parseInt(formData.employeeId)
        })
      });

      if (response.ok) {
        setMessage('Expense added successfully!');
        setMessageType('success');
        setFormData({ employeeId: '', amount: '', description: '', date: '' });
      } else {
        const error = await response.text();
        setMessage(error);
        setMessageType('error');
      }
    } catch (error) {
      setMessage('Error adding expense');
      setMessageType('error');
    }
  };

  return (
    <div className="container">
      <div className="card card-centered">
        <h2>Add Expense</h2>
        
        {message && (
          <div className={`alert ${messageType === 'success' ? 'alert-success' : 'alert-danger'}`}>
            {message}
          </div>
        )}
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Employee ID</label>
            <input
              type="number"
              name="employeeId"
              className="form-control"
              value={formData.employeeId}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Amount ($)</label>
            <input
              type="number"
              name="amount"
              className="form-control"
              value={formData.amount}
              onChange={handleChange}
              step="0.01"
              min="0.01"
              required
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Description</label>
            <textarea
              name="description"
              className="form-control"
              value={formData.description}
              onChange={handleChange}
              rows="4"
              placeholder="Enter expense description (5-200 characters)"
              required
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Date</label>
            <input
              type="date"
              name="date"
              className="form-control"
              value={formData.date}
              onChange={handleChange}
              max={new Date().toISOString().split('T')[0]}
              required
            />
          </div>
          
          <button type="submit" className="btn btn-primary btn-submit">
            Add Expense
          </button>
        </form>
      </div>
    </div>
  );
}

export default ExpenseForm;