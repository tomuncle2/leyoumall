package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 14:33 2020/9/28
 * @description: CartService
 */
public interface CartService {

    /***
     * 添加购物车
     * @date 16:23 2020/9/28
     * @param cart
     * @return void
     */
    boolean addCart(Cart cart);

    /***
     * 修改购物车
     * @date 16:23 2020/9/28
     * @param cart
     * @return void
     */
    boolean updateCart(Cart cart);

    /***
     * 删除购物车
     * @date 16:23 2020/9/28
     * @param skuId
     * @return void
     */
    boolean deleteCart(String skuId);

    /***
     * 查询所有购物车
     * @date 16:25 2020/9/28
     * @param
     * @return java.util.List<com.leyou.cart.pojo.Car>
     */
    List<Cart> queryCart();
}