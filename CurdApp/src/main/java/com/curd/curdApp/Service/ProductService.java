package com.curd.curdApp.Service;

//import com.example.App.DTO.ProductDTO;
import com.curd.curdApp.Entity.Product;
//import com.example.App.entity.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product getById(Long id);
    List<Product> getAll();
    Product update(Long id, Product product);
    void delete(Long id);
}
