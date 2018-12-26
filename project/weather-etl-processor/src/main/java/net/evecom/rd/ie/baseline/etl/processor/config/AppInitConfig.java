package net.evecom.rd.ie.baseline.etl.processor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.core.rbac.model.service.UserService;
import net.evecom.rd.ie.baseline.etl.processor.model.entity.ProcessorChain;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AppInitConfig implements CommandLineRunner {

    @Resource(name = "redisClient")
    protected RedisClient redisClient;
    @Resource
    private ResourceService resourceService;
    @Resource
    private UserService userService;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("权限数据初始化");
        ProcessorChain processorChain = new ProcessorChain();
        processorChain.setChainName("test");
        processorChain.setChainDesc("123456");
        resourceService.add(processorChain);
    }
}
