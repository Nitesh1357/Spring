//package com.nkm.Testapp.Controller;
//
//import com.nkm.Testapp.Entity.Product;
//import com.nkm.Testapp.Service.ProductService;
//
//import java.util.List;
//@RestController
//@RequestMapping("/api/products")
//public class ProductController {
//
//    private final ProductService service;
//
//    public ProductController(ProductService service) {
//        this.service = service;
//    }
//
//    @PostMapping ("/api/products")
//    public ResponseEntity<Product> create(@RequestBody Product product) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(product));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getById(@PathVariable Long id) {
//        Product product =service.getById(id);
//        return ResponseEntity.ok(service.getById(id));
//    }
//
//    @GetMapping ("/getall")
//    public ResponseEntity<List<Product>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
//        return ResponseEntity.ok(service.update(id, product));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
//
