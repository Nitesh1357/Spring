package com.example.App.Service;
//import com.example.App.DTO.ProductDTO;
import com.example.App.entity.Product;
//import com.example.App.entity.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product getById(Long id);
    List<Product> getAll();
    Product update(Long id, Product product);
    void delete(Long id);
}


//
//public interface ProductService {
//    ProductDTO createProduct(ProductDTO dto);
//    ProductDTO getProductById(Long id);
//    List<ProductDTO> getAllProducts();
//    ProductDTO updateProduct(Long id, ProductDTO dto);
//    void deleteProduct(Long id);
//}



