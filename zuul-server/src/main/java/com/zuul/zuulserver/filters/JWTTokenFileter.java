package com.zuul.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.zuul.zuulserver.filters
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-22 17:47
 * @Description: ...
 */
public class JWTTokenFileter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;

    /**
     * 过滤器：pre
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }
    /**
     * 过滤器顺序
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /**
     * 始终对请求进行过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 在这里实现对JWT令牌的校验处理
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            System.out.println("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        System.out.println("access token ok");
        return null;
    }
}
