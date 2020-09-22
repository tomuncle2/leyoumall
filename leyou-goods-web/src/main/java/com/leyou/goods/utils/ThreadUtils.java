package com.leyou.goods.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: 蔡迪
 * @date: 11:23 2020/9/22
 * @description:
 */
public class ThreadUtils {
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }

    public static Future execute(Callable runnable) {
        return es.submit(runnable);
    }
}