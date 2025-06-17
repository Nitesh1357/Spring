package com.nkm.Testapp.Service;
import com.nkm.Testapp.Entity.Product;
import com.nkm.Testapp.Exception.ResourceNotFoundException;
import com.nkm.Testapp.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product create(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @Override
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Override
    public Product update(Long id, Product updatedProduct) {
        Product existing = getById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        Product existing = getById(id);
        repo.delete(existing);
    }
}

