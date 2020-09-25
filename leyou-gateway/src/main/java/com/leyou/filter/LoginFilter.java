package com.leyou.filter;

import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.config.JwtProperties;
import com.leyou.config.WhiteListProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:06 2020/7/25
 * @description: 登录认证过滤器
 */
@Component
@Slf4j
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private WhiteListProperties filterProperties;

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
        return 5;
    }

    // 是否执行过滤器
    @Override
    public boolean shouldFilter() {
        // 是否是白名单
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        String uri = httpServletRequest.getRequestURI();

        // 查询白名单 是白名单就不再执行run 去校验token
        List<String> allowPaths = filterProperties.getAllowPaths();
        if (!CollectionUtils.isEmpty(allowPaths)) {
            for (String allowPath : allowPaths) {
                if (uri.startsWith(allowPath)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 执行逻辑 返回null则什么也不做
    @Override
    public Object run() throws ZuulException {
        // 获取当前线程的上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        // 获取token
        String token = CookieUtils.getCookieValue(httpServletRequest, this.jwtProperties.getCookieName());
        log.warn("loginFilter拦截请求，token校！！{}", token);
        // 可以做权限校验 动态url路由匹配

        try {
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("loginFilter拦截请求，token校验失败！！ {}", token);
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }

        return null;
    }
}