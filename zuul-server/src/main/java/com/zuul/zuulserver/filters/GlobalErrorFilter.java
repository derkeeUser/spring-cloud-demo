package com.zuul.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.zuul.zuulserver.filters
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-23 14:38
 * @Description: ...
 */
public class GlobalErrorFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 在这里实现对JWT令牌的校验处理
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        HttpServletRequest request = ctx.getRequest();
        LOGGER.error("[ErrorFilter] error message:{}", throwable.getCause().getMessage());
        ctx.set("error.status_code",HttpServletRequest.BASIC_AUTH);
        ctx.set("error.exception", throwable.getCause());
        return null;
    }
}
