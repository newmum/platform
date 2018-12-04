package net.evecom.rd.ie.baseline.etl.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {
		"net.evecom.etl.mgr",
		"net.evecom.core"})
public class EtlMgrApp {

	public static void main(String[] args) {
		SpringApplication.run(EtlMgrApp.class, args);
	}

}
