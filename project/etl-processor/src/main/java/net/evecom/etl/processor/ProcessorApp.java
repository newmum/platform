package net.evecom.etl.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {
		"net.evecom.etl.processor",
        "net.evecom.core"})
public class ProcessorApp {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApp.class, args);
	}

}
