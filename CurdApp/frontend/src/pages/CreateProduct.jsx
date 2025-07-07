import React, { useState } from "react";
import axios from "axios";

export default function CreateProduct() {
  const [product, setProduct] = useState({ name: "", description: "", price: "" });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    try {
      await axios.post("http://localhost:8080/api/products/create", product, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert("Product created");
      setProduct({ name: "", description: "", price: "" });
    } catch (err) {
      alert("Failed to create: " + (err.response?.data || err.message));
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input placeholder="Name" onChange={e => setProduct({ ...product, name: e.target.value })} />
      <input placeholder="Description" onChange={e => setProduct({ ...product, description: e.target.value })} />
      <input placeholder="Price" type="number" onChange={e => setProduct({ ...product, price: e.target.value })} />
      <button type="submit">Create</button>
    </form>
  );
}
