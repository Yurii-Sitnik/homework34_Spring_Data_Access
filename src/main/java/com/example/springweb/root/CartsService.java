package com.example.springweb.root;

import com.example.springweb.persistence.entity.Cart;
import com.example.springweb.persistence.repository.CartsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartsService {

    private final CartsDao cartsDao;

    public void addProductToCartById(Integer cart_id, Integer product_id) {
        this.cartsDao.addProductById(cart_id, product_id);
    }

    public void deleteById(Integer cart_id) {
        this.cartsDao.deleteById(cart_id);
    }

    public Cart findById(Integer cart_id) {
        return this.cartsDao.findById(cart_id);
    }

    public int createCart(Cart cart) {
        return cartsDao.create(cart);
    }

    public List<Cart> getAllCarts() {
        return cartsDao.findAll();
    }
}
