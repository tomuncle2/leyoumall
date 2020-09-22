package com.leyou;


import com.leyou.goods.service.GoodsHtmlService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeyouGoodsWebApplicationTests {

    @Autowired
    private GoodsHtmlService service;

    @Test
    void contextLoads() {
        service.createHtml(2L);
    }

}
