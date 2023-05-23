package com.example.springweb.web;

import com.example.springweb.persistence.entity.Cart;
import com.example.springweb.root.CartsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartsService cartsService;

    public CartController(CartsService cartsService) {
        this.cartsService = cartsService;
    }

    @PostMapping("/add/{cart_id}/{product_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addById(
            @PathVariable("cart_id") Integer cart_id,
            @PathVariable("product_id") Integer product_id) {
        cartsService.addProductToCartById(cart_id, product_id);
    }

    @GetMapping("/delete/{cart_id}")
    public void deleteById(@PathVariable("cart_id") Integer cart_id) {
        cartsService.deleteById(cart_id);
    }

    @GetMapping("/{cart_id}")
    public Cart findById(@PathVariable("cart_id") Integer cart_id) {
        return cartsService.findById(cart_id);
    }

    @PostMapping
    public int createCart(@RequestBody Cart cart) {
        return cartsService.createCart(cart);
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartsService.getAllCarts();
    }
}
