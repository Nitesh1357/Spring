package com.nkm.Testapp.Entity;
import jakarta.persistence.*;
import lombok.*;


    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "products")
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;
        @Column
        private String description;
        @Column
        private double price;


        public Product (String name,String description, Double price) {
            super();
            this.name = name;
            this.price = price;
            this.description=description;
        }


    }

