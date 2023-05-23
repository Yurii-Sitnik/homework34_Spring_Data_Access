package com.example.springweb.persistence.repository;

import com.example.springweb.persistence.entity.Cart;
import com.example.springweb.persistence.entity.Product;
import com.example.springweb.web.mapper.CartMapper;
import com.example.springweb.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartsDao {
    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;

    public int create(Cart cart) {
        String sqlInsertCart = "INSERT INTO carts DEFAULT VALUES";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCart, Statement.RETURN_GENERATED_KEYS);
            return preparedStatement;
        }, generatedKeyHolder);

        int cartId = (int) generatedKeyHolder.getKeys().get("id");
        cart.setId(cartId);

        String sqlInsertCartProduct = "INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?)";
        for (Product product : cart.getProducts()) {
            if (productDao.findById(product.getId()) != null) {
                jdbcTemplate.update(sqlInsertCartProduct, cartId, product.getId());
            }
        }
        return cartId;
    }

    public void addProductById(Integer cartId, Integer productId) {


        String s1 = "SELECT \"name\" FROM \"Products\" WHERE \"id\" = " + productId;
        String productName = jdbcTemplate.queryForObject(s1, String.class);

        String s2 = "SELECT \"price\" FROM \"Products\" WHERE \"id\" = " + productId;
        Integer productPrice = jdbcTemplate.queryForObject(s2, Integer.class);

        String SQL = "INSERT INTO public.\"Carts\" (cart_id, product_id, product_name, product_price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(SQL, cartId, productId, productName, productPrice);
    }


    public void deleteById(int id) {
        String sqlDeleteCartProducts = "DELETE FROM carts_products WHERE cart_id = ?";
        jdbcTemplate.update(sqlDeleteCartProducts, id);

        String sqlDeleteCart = "DELETE FROM carts WHERE id = ?";
        jdbcTemplate.update(sqlDeleteCart, id);
    }

    public Cart findById(int id) {
        String sqlCart = "SELECT * FROM carts WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlCart, new CartMapper(), id);
    }

    public List<Cart> findAll() {
        String sql = "SELECT * FROM carts";
        List<Cart> carts = jdbcTemplate.query(sql, new CartMapper());

        for (Cart cart : carts) {
            String sqlProducts = "SELECT * FROM products p JOIN carts_products cp ON p.id = cp.product_id WHERE cp.cart_id = ?";
            List<Product> products = jdbcTemplate.query(sqlProducts, new ProductMapper(), cart.getId());

            cart.setProducts(products);
        }
        return carts;
    }
}

