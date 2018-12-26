package com.evecom.rd.ie.baseline.cloud.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}

}
