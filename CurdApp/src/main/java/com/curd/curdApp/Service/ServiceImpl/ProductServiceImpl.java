package com.curd.curdApp.Service.ServiceImpl;
import com.curd.curdApp.Repository.ProductRepository;
//import com.example.App.DTO.ProductDTO;
import com.curd.curdApp.Exception.ResourceNotFoundException;
import com.curd.curdApp.Service.ProductService;
import com.curd.curdApp.Entity.Product;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//
import java.util.List;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        log.info("Creating new product: {}", product.getName());
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        log.info("Fetching product by id: {}", id);
        return productRepository.findById(id).orElseThrow(() -> {
            log.error("Product not found with id: {}", id);
            return new ResourceNotFoundException("Product not found with id " + id);
        });
    }

    @Override
    public List<Product> getAll() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public Product update(Long id, Product updatedProduct) {
        log.info("Updating product with id: {}", id);
        Product existing = getById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);
        Product existing = getById(id);
        productRepository.delete(existing);
    }
}