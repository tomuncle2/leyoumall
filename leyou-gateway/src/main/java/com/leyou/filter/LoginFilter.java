package com.leyou.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 蔡迪
 * @date: 17:06 2020/7/25
 * @description: 登录认证过滤器
 */
@Component
@Slf4j
public class LoginFilter extends ZuulFilter {

    /* 按照执行阶段从先到后分为四种类型的过滤器
    * pre: 请求过来 未到路由器阶段
    * routing 到达路由器阶段后
    * error 前面阶段出现异常
    * post 服务执行完后，相应前
    * */
    @Override
    public String filterType() {
        return "pre";
    }

    // 执行优先级 值越小优先级越高
    @Override
    public int filterOrder() {
        return 20;
    }

    // 是否执行过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 执行逻辑 返回null则什么也不做
    @Override
    public Object run() throws ZuulException {
        // 获取当前线程的上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        String token = httpServletRequest.getParameter("token");
//        if (StringUtils.isNotBlank(token)) {
//            // 校验通过，继续执行下一阶段
//            requestContext.set("token", token);
//            log.warn("loginFilter拦截请求，token校验成功！！");
//        } else {
//            // 过滤该请求，不进行路由
//            requestContext.setSendZuulResponse(false);
//            // 响应码
//            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
//            // 响应内容
//            requestContext.setResponseBody("{\"status\":\"401\", \"text\":\"request error!\"}");
//            log.warn("loginFilter拦截请求，token校验失败！！");
//        }

        log.warn("loginFilter拦截请求，token校验成功！！");
        return null;
    }
}