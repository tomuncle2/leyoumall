package com.leyou.goods.service.impl;

import com.leyou.goods.service.GoodsHtmlService;
import com.leyou.goods.service.GoodsService;
import com.leyou.goods.utils.ThreadUtils;
import com.leyou.pojo.Spu;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author: 蔡迪
 * @date: 11:22 2020/9/22
 * @description:
 */
@Service
@Slf4j
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;
    /**
     * 创建html页面
     *
     * @param spuMap
     * @throws Exception
     */
    @Override
    public void createHtml(Map<String, Object> spuMap) {
        Spu spu = (Spu)spuMap.get("spu");
        PrintWriter writer = null;
        try {
            // 获取页面数据
            //Map<String, Object> spuMap = this.goodsService.loadGoodsDetail(spuId);

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(spuMap);

            // 创建输出流
            File file = new File("D:\\development\\nginx-1.14.0\\html\\item\\" + spu.getId() + ".html");
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            log.error("页面静态化出错：{}，"+ e, spu.getId());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    @Override
    public void createHtml(Long spuId) {

        PrintWriter writer = null;
        try {
            // 获取页面数据
            Map<String, Object> spuMap = this.goodsService.loadGoodsDetail(spuId);

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(spuMap);

            // 创建输出流
            File file = new File("D:\\development\\nginx-1.14.0\\html\\item\\" + spuId + ".html");
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("items", context, writer);
        } catch (Exception e) {
            log.error("页面静态化出错：{}，"+ e, spuId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    public void asyncExcute(Long spuId) {
        ThreadUtils.execute(()->createHtml(spuId));
        /*ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                createHtml(spuId);
            }
        });*/
    }

    /**
     * 删除
     * @date 17:03 2020/9/22
     * @param id
     * @return void
     */
    @Override
    public void deleteHtml(Long id) {
        File file = new File("D:\\development\\nginx-1.14.0\\html\\item\\" + id + ".html");
        file.deleteOnExit();
    }
}