package com.example.springapp.controller;

import com.example.springapp.model.Product;
import com.example.springapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}