package com.example.springweb.web;

//import com.example.springweb.model.UserDto;
import com.example.springweb.persistence.entity.Product;
import com.example.springweb.root.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/all")
    public List<Product> findAll() {
        return productService.findAll();
    }

   @GetMapping("/{id}")
    public Optional<Product> getById (@PathVariable("id") Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {

        productService.addProduct(product);

    }
    @GetMapping("/delete/{id}")
    public void deleteByIdPathParam(@PathVariable("id") Integer id) {
        productService.deleteById(id);
    }
}


