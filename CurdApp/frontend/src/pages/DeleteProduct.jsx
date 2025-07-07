import React, { useState } from "react";
import axios from "axios";

export default function DeleteProduct() {
  const [id, setId] = useState("");

  const handleDelete = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    if (!token) {
      alert("Please login first.");
      return;
    }

    try {
      await axios.delete(`http://localhost:8080/api/products/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      alert("Product deleted successfully");
      setId("");
    } catch (err) {
      alert("Delete failed: " + (err.response?.data || err.message));
    }
  };

  return (
    <form onSubmit={handleDelete} className="space-y-4">
      <input
        placeholder="Product ID to delete"
        value={id}
        onChange={(e) => setId(e.target.value)}
        required
      />
      <button type="submit">Delete Product</button>
    </form>
  );
}
