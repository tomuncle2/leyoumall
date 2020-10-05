package com.leyou.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.leyou.auth.dto.UserInfoDTO;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.common.utils.RedisUtils;
import com.leyou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 蔡迪
 * @date: 14:34 2020/9/28
 * @description:
 */
@Service
public class CartServiceImpl implements CartService  {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    private final String REDIS_KEY = "redis:cart";
    /***
     * 添加购物车
     * @date 16:23 2020/9/28
     * @param cart
     * @return void
     */
    @Override
    public boolean addCart(Cart cart) {

        // 获取用户信息
        UserInfoDTO userInfoDTO = LoginInterceptor.getLoginUser();

        String key = REDIS_KEY + userInfoDTO.getId();

        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(key);

        boolean isExist = boundHashOps.hasKey(String.valueOf(cart.getSkuId()));
        if (!isExist) {
            // 新增 item 查询spu
            Sku sku = goodsClient.querySkuById(cart.getSkuId()).getBody();
            if (null == sku) {
                return false;
            }
            cart.setImage(sku.getImages());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setUserId(userInfoDTO.getId());
        } else {
            // 更新
            String json = (String)boundHashOps.get(cart.getSkuId());
            Cart alreadyExistCart = JSON.parseObject(json, Cart.class);
            alreadyExistCart.setNum(alreadyExistCart.getNum() + cart.getNum());
            cart = alreadyExistCart;
        }
        boundHashOps.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
        return true;
    }

    /***
     * 修改购物车
     * @date 16:23 2020/9/28
     * @param cart
     * @return void
     */
    @Override
    public boolean updateCart(Cart cart) {
        // 获取用户信息
        UserInfoDTO userInfoDTO = LoginInterceptor.getLoginUser();

        String key = REDIS_KEY + userInfoDTO.getId();

        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(key);

        boolean isExist = boundHashOps.hasKey(String.valueOf(cart.getSkuId()));
        if (!isExist) {
            // 新增 item 查询spu
            return false;
        } else {
            // 更新
            String json = (String)boundHashOps.get(String.valueOf(cart.getSkuId()));
            Cart alreadyExistCart = JSON.parseObject(json, Cart.class);
            alreadyExistCart.setNum(cart.getNum() );
            cart = alreadyExistCart;
        }
        boundHashOps.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
        return true;
    }

    /***
     * 删除购物车
     * @date 16:23 2020/9/28
     * @param skuId
     * @return void
     */
    @Override
    public boolean deleteCart(String skuId) {
        // 获取用户信息
        UserInfoDTO userInfoDTO = LoginInterceptor.getLoginUser();

        String key = REDIS_KEY + userInfoDTO.getId();

        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(key);

        boolean isExist = boundHashOps.hasKey(String.valueOf(skuId));
        if (!isExist) {
            // 新增 item 查询spu
            return false;
        } else {
            // 更新
            Long result = boundHashOps.delete(skuId);
            return true;
        }
    }

    /***
     * 查询所有购物车
     * @date 16:25 2020/9/28
     * @param
     * @return java.util.List<com.leyou.cart.pojo.Car>
     */
    @Override
    public List<Cart> queryCart() {
        // 获取用户信息
        UserInfoDTO userInfoDTO = LoginInterceptor.getLoginUser();

        String key = REDIS_KEY + userInfoDTO.getId();

        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps(key);

        //查询所有数据
        List<Object> list = boundHashOps.values();
        List<Cart> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object o : list) {
                Cart cart = JSON.parseObject(String.valueOf(o), Cart.class);
                resultList.add(cart);
            }
        }
        return resultList;
    }
}