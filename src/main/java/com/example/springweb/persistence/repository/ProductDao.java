package com.example.springweb.persistence.repository;

import com.example.springweb.persistence.entity.Product;
import com.example.springweb.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM Products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductMapper());
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM Products";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

