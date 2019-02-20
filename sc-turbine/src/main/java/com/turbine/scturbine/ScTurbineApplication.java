package com.turbine.scturbine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableDiscoveryClient
@EnableTurbine
@SpringBootApplication
public class ScTurbineApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ScTurbineApplication.class).web(true).run(args);
    }

}
