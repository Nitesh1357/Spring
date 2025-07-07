import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import CreateProduct from "./pages/CreateProduct";
import ReadProducts from "./pages/ReadProducts";
import UpdateProduct from "./pages/UpdateProduct";
import DeleteProduct from "./pages/DeleteProduct";

function App() {
  return (
    <Router>
      <nav style={{ marginBottom: "20px" }}>
        <Link to="/create">Create</Link> | {" "}
        <Link to="/read">Read</Link> | {" "}
        <Link to="/update">Update</Link> | {" "}
        <Link to="/delete">Delete</Link> | {" "}
        <Link to="/login">Login</Link> | {" "}
        <Link to="/register">Register</Link>
      </nav>

      <Routes>
        <Route path="/create" element={<CreateProduct />} />
        <Route path="/read" element={<ReadProducts />} />
        <Route path="/update" element={<UpdateProduct />} />
        <Route path="/delete" element={<DeleteProduct />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </Router>
  );
}

export default App;
