package com.example.ecommerce.model.repository;

import com.example.ecommerce.model.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProductRepository {
    private JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> search() {

        return jdbcTemplate.query(
                "select * from products",
                new ProductMapper()
        );
    }

    public Product search(int id) {
        return jdbcTemplate.queryForObject(
                "select * from products where id = ?",
                new ProductMapper(),
                id
        );
    }

    public List<Product> search(String name) {
        return jdbcTemplate.query(
                "select * from products where name like ?",
                new ProductMapper(),
                "%" + name + "%"
        );
    }

    public Product create(Product product) throws Exception {
        int insert = jdbcTemplate.update(
                "insert into products (name, price, photo_url) values (?, ?, ?)",
                product.getName(),
                product.getPrice(),
                product.getPhotoUrl()
        );
        if (insert == 1) {
            int id = jdbcTemplate.queryForObject("select max(id) from products", Integer.class);
            product.setId(id);
            return product;
        }
        throw new Exception("Failed to register the product!");
    }

    public Product update(Product product) {
        int update = jdbcTemplate.update(
                "update products set nome = ?, price = ?, photo_url = ? where id = ?",
                product.getName(),
                product.getPrice(),
                product.getPhotoUrl(),
                product.getId()
        );
        if( update == 1 ) {
            System.out.println("Product:(ID - "+ product.getId() + " ) " + product.getName() + " updated!");
        }
        return product;
    }

    public void delete(int id) {
        int delete = jdbcTemplate.update("delete from products where id = ?",id);
        if( delete == 1 ) {
            System.out.println("Product:(ID - " + id + ") foi deleted!");
        }
    }
}
