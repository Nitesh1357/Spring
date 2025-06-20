package com.example.App.ServiceImpl;

import com.example.App.Repository.ProductRepository;

//import com.example.App.DTO.ProductDTO;
import com.example.App.Exception.ResourceNotFoundException;
import com.example.App.Service.ProductService;
import com.example.App.entity.Product;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
//
import java.util.List;


//import java.util.stream.Collectors;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public ProductDTO createProduct(ProductDTO dto) {
//        Product product = modelMapper.map(dto, Product.class);
//        Product saved = productRepository.save(product);
//        return modelMapper.map(saved, ProductDTO.class);
//    }
//
//    @Override
//    public ProductDTO getProductById(Long id) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
//        return modelMapper.map(product, ProductDTO.class);
//    }
//
//    @Override
//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAll()
//                .stream()
//                .map(product -> modelMapper.map(product, ProductDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ProductDTO updateProduct(Long id, ProductDTO dto) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
//        product.setName(dto.getName());
//        product.setDescription(dto.getDescription());
//        product.setPrice(dto.getPrice());
//        Product updated = productRepository.save(product);
//        return modelMapper.map(updated, ProductDTO.class);
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//        productRepository.deleteById(id);
//    }
//}
//


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






//@Service
//public class ProductServiceImpl implements ProductService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
//
//    private final UserTaskRepository repo;
//
//    public ProductServiceImpl(UserTaskRepository repo) {
//        this.repo = repo;
//    }
//
//    @Override
//    public Product create(Product product) {
//        logger.info("Creating new product: {}", product.getName());
//        return repo.save(product);
//    }
//
//    @Override
//    public Product getById(Long id) {
//        logger.info("Fetching product by id: {}", id);
//        return repo.findById(id).orElseThrow(() -> {
//            logger.error("Product not found with id: {}", id);
//            return new ResourceNotFoundException("Product not found with id " + id);
//        });
//    }
//
//    @Override
//    public List<Product> getAll() {
//        logger.info("Fetching all products Mishra");
//        return repo.findAll();
//    }
//
//    @Override
//    public Product update(Long id, Product updatedProduct) {
//        logger.info("Updating product with id: {}", id);
//        Product existing = getById(id);
//        existing.setName(updatedProduct.getName());
//        existing.setDescription(updatedProduct.getDescription());
//        existing.setPrice(updatedProduct.getPrice());
//        logger.debug("Updated product details: {}", updatedProduct);
//        return repo.save(existing);
//    }
//
//    @Override
//    public void delete(Long id) {
//        logger.info("Deleting product with id: {}", id);
//        Product existing = getById(id);
//        repo.delete(existing);
//    }
//}
