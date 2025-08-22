import React, { useState, useEffect } from 'react';

function ExpenseList() {
  const [expenses, setExpenses] = useState([]);
  const [filter, setFilter] = useState('All');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8081/api/expenses');
      const data = await response.json();
      setExpenses(data);
    } catch (error) {
      console.error('Error fetching expenses:', error);
    } finally {
      setLoading(false);
    }
  };

  const updateStatus = async (id, status, remarks) => {
    try {
      await fetch(`http://localhost:8081/api/expenses/${id}/status`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status, remarks })
      });
      fetchExpenses();
    } catch (error) {
      console.error('Error updating status:', error);
    }
  };

  const handleApprove = (id) => {
    updateStatus(id, 'APPROVED', 'Approved as per policy');
  };

  const handleReject = (id) => {
    const remarks = prompt('Enter rejection reason:');
    if (remarks && remarks.trim()) {
      updateStatus(id, 'REJECTED', remarks.trim());
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  const formatAmount = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  const getStatusBadge = (status) => {
    const badgeClass = {
      'APPROVED': 'badge-success',
      'REJECTED': 'badge-danger',
      'PENDING': 'badge-warning'
    }[status] || 'badge-warning';
    
    return <span className={`badge ${badgeClass}`}>{status}</span>;
  };

  const filteredExpenses = expenses.filter(expense => 
    filter === 'All' || expense.status === filter
  );

  if (loading) {
    return (
      <div className="container">
        <div className="card">
          <div className="empty-state">
            <h3>Loading expenses...</h3>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="card">
        <h2>Expense List</h2>
        
        <div className="filter-container">
          <label className="filter-label">Filter by Status:</label>
          <select 
            className="filter-select" 
            value={filter} 
            onChange={(e) => setFilter(e.target.value)}
          >
            <option value="All">All Expenses</option>
            <option value="PENDING">Pending</option>
            <option value="APPROVED">Approved</option>
            <option value="REJECTED">Rejected</option>
          </select>
        </div>

        {filteredExpenses.length === 0 ? (
          <div className="empty-state">
            <h3>No expenses found</h3>
            <p>No expenses match the selected filter criteria.</p>
          </div>
        ) : (
          <div className="table-container">
            <table className="table">
              <thead>
                <tr>
                  <th>Employee ID</th>
                  <th>Amount</th>
                  <th>Description</th>
                  <th>Date</th>
                  <th>Status</th>
                  <th>Remarks</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredExpenses.map(expense => (
                  <tr key={expense.id}>
                    <td>{expense.employeeId}</td>
                    <td>{formatAmount(expense.amount)}</td>
                    <td>
                      <div style={{ maxWidth: '200px', wordWrap: 'break-word' }}>
                        {expense.description}
                      </div>
                    </td>
                    <td>{formatDate(expense.date)}</td>
                    <td>{getStatusBadge(expense.status)}</td>
                    <td>
                      <div style={{ maxWidth: '150px', wordWrap: 'break-word' }}>
                        {expense.remarks || '-'}
                      </div>
                    </td>
                    <td>
                      {expense.status === 'PENDING' ? (
                        <div className="action-buttons">
                          <button 
                            className="btn btn-success"
                            onClick={() => handleApprove(expense.id)}
                            title="Approve expense"
                          >
                            Approve
                          </button>
                          <button 
                            className="btn btn-danger"
                            onClick={() => handleReject(expense.id)}
                            title="Reject expense"
                          >
                            Reject
                          </button>
                        </div>
                      ) : (
                        <span style={{ color: '#6c757d', fontStyle: 'italic' }}>No actions</span>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default ExpenseList;