package com.example.App.Controller;

import com.example.App.Service.ProductService;
import com.example.App.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        logger.info("Creating a new product: {}", product);
        Product createdProduct = service.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Product product = service.getById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAll() {
        logger.info("Fetching all products Nitesh");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        logger.info("Updating product with ID: {}", id);
        Product updated = service.update(id, product);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/user")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User user) {
        return "Hello, " + user.getAttribute("name") + " (" + user.getAttribute("email") + ")";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
