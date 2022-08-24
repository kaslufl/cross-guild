package com.example.ecommerce.controller;


import com.example.ecommerce.model.entity.Product;
import com.example.ecommerce.model.exception.ProductBadRequestException;
import com.example.ecommerce.model.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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
}
