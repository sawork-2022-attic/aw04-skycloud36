package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosService {

    public void checkout(Cart cart);

    public Cart add(Cart cart, Product product, int amount);

    public Cart add(Cart cart, String productId, int amount);

    public Cart delete(Cart cart, String productId);

    public Cart modify(Cart cart, String productId, int amount);

    public Cart empty(Cart cart);

    public List<Product> products();

    public Product randomProduct();
}
