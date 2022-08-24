package com.example.ecommerce.model.repository;

import com.example.ecommerce.model.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getFloat("price"));
        product.setPhotoUrl(resultSet.getString("photo_url"));

        return product;
    }
}
