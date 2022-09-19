package com.example.ecommerce.controller;


import com.example.ecommerce.model.entity.Product;
import com.example.ecommerce.model.exception.ProductBadRequestException;
import com.example.ecommerce.model.exception.ProductNotFoundException;
import com.example.ecommerce.model.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(JdbcTemplate jdbcTemplate) {
        this.productRepository = new ProductRepository(jdbcTemplate);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product create(@RequestBody Product product) throws Exception {
        try {
            return productRepository.create(product);
        } catch (DataIntegrityViolationException e) {
            throw new ProductBadRequestException();
        }
    }

    @GetMapping
    public List<Product> getProducts() {
        try {
            return productRepository.search();
        } catch (DataIntegrityViolationException e) {
            throw new ProductBadRequestException();
        }
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        try {
            return productRepository.search(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateProductById(@PathVariable int id, @RequestBody Product product) {
        try {
            product.setId(id);
            productRepository.update(product);
        } catch (DataIntegrityViolationException e) {
            throw new ProductBadRequestException();
        }
    }

}
