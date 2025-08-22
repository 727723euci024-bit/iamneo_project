import React, { useState, useEffect } from 'react';

function ProductList() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/products')
      .then(response => response.json())
      .then(data => setProducts(data));
  }, []);

  return (
    <table style={{ border: '1px solid black', borderCollapse: 'collapse' }}>
      <thead>
        <tr>
          <th style={{ border: '1px solid black', padding: '8px' }}>ID</th>
          <th style={{ border: '1px solid black', padding: '8px' }}>Name</th>
          <th style={{ border: '1px solid black', padding: '8px' }}>Price</th>
        </tr>
      </thead>
      <tbody>
        {products.map(product => (
          <tr key={product.id}>
            <td style={{ border: '1px solid black', padding: '8px' }}>{product.id}</td>
            <td style={{ border: '1px solid black', padding: '8px' }}>{product.name}</td>
            <td style={{ border: '1px solid black', padding: '8px' }}>${product.price}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ProductList;