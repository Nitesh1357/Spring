import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ReadProducts() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    axios.get("http://localhost:8080/api/products/getall", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => setProducts(res.data))
      .catch(err => alert("Failed to fetch: " + (err.response?.data || err.message)));
  }, []);

  return (
    <div>
      <h2>All Products</h2>
      {products.map(p => (
        <div key={p.id}>
          <strong>{p.name}</strong>: {p.description} - ₹{p.price}
        </div>
      ))}
    </div>
  );
}
