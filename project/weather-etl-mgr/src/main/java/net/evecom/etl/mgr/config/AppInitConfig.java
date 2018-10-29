package net.evecom.etl.mgr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.core.rbac.model.service.UserService;
import net.evecom.utils.database.redis.RedisClient;
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
    }
}
