import React, { useState } from "react";
import axios from "axios";

export default function UpdateProduct() {
  const [id, setId] = useState("");
  const [product, setProduct] = useState({
    name: "",
    description: "",
    price: "",
  });

  const handleUpdate = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    if (!token) {
      alert("Please login first.");
      return;
    }

    try {
      await axios.put(`http://localhost:8080/api/products/${id}`, product, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert("Product updated successfully");
      setId("");
      setProduct({ name: "", description: "", price: "" });
    } catch (err) {
      alert("Update failed: " + (err.response?.data || err.message));
    }
  };

  return (
    <form onSubmit={handleUpdate} className="space-y-4">
      <input
        placeholder="Product ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
        required
      />
      <input
        placeholder="Name"
        value={product.name}
        onChange={(e) => setProduct({ ...product, name: e.target.value })}
      />
      <input
        placeholder="Description"
        value={product.description}
        onChange={(e) => setProduct({ ...product, description: e.target.value })}
      />
      <input
        placeholder="Price"
        type="number"
        value={product.price}
        onChange={(e) => setProduct({ ...product, price: e.target.value })}
      />
      <button type="submit">Update Product</button>
    </form>
  );
}
