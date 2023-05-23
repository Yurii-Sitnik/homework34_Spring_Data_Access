package com.example.springweb.root;

import com.example.springweb.persistence.entity.Product;
import com.example.springweb.persistence.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(this.productDao.findById(id));
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void addProduct(Product product) {
        this.productDao.addProduct(product);
    }

    public void deleteById(Integer id) {
        this.productDao.deleteById(id);
    }
}

