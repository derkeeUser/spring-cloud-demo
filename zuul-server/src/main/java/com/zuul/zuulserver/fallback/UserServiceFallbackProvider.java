package com.zuul.zuulserver.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @BelongsProject: scdemo
 * @BelongsPackage: com.zuul.zuulserver
 * @Author: chenxiaoming
 * @CreateTime: 2019-02-22 15:33
 * @Description: Zuul路由实现响应的服务降级处理功能
 */
public class UserServiceFallbackProvider implements ZuulFallbackProvider {
    @Override
    public String getRoute() {
        //此处是route的名称，而不是服务的名称
        //如果这里写成大写USER-SERVICE，则将无法起到回退作用
        return "user-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        //创建一个Fallback响应
        return new ClientHttpResponse() {
            //创建响应的状态、状态码的定义
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String mockUserJson = "{\"id\":-3,\n\"nickname\":\"fakeUser\",\n\"avatar\":\"/users/avatar/user.png\"}";
                return new ByteArrayInputStream(mockUserJson.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                //需要将返回的格式设置JSON
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
