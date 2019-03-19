package com.productservice.productdemo;

import com.productservice.productdemo.util.JWTOAuthTokenInterceptor;
import com.userservice.userdemo.user.entity.UserMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

//@EnableBinding(Sink.class)
@EnableResourceServer
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ProductDemoApplication {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(value = "restTemplate")
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        List interceptors = template.getInterceptors();
        if (interceptors == null) {
            template.setInterceptors(Collections.singletonList(new JWTOAuthTokenInterceptor()));
        } else {
            interceptors.add(new JWTOAuthTokenInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
    }

    /**
     * 增加了@Primary注解，这样当在其他地方需要使用时，默认会先使用该Bean
     * @return
     */
    @Primary
    @Bean(value = "lbcRestTemplate")
    RestTemplate lbcRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductDemoApplication.class, args);
    }

    //@StreamListener(Sink.INPUT)
    //public void onUserMsgSink(UserMsg userMsg){
    //    this.logger.info("receive user msg:{}",userMsg);
    //}

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

}