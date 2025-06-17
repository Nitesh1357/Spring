package com.nkm.Testapp.Repository;

import com.nkm.Testapp.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

