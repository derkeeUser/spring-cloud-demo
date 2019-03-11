package com.zuul.zuulserver.component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.zuul.zuulserver.component
 * @Author: chenxiaoming
 * @CreateTime: 2019-03-08 14:51
 * @Description: ...
 */
@Component
public class TraceIdFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    @Autowired
    private Tracer tracer;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().addHeader("scd-trace-id",this.tracer.getCurrentSpan().traceIdString());
        return null;
    }
}
