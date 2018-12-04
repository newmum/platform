package net.evecom.rd.ie.baseline.etl.mgr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AppInitConfig implements CommandLineRunner {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("权限数据初始化");
    }
}
