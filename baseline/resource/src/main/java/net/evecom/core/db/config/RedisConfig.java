package net.evecom.core.db.config;

import net.evecom.utils.database.redis.JedisConfig;
import net.evecom.utils.database.redis.RedisClient;
import net.evecom.utils.file.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author： zhengc
 * @date： 2018年5月30日17:13:38
 */
@Configuration
public class RedisConfig {

    @Bean(name = "redisClient", value = "redisClient")
    public RedisClient redisTool() {
        PropertiesUtils global = new PropertiesUtils(RedisConfig.class.getClassLoader().getResourceAsStream("redis.properties"));
        JedisConfig config = new JedisConfig();
        config.setIp(global.getKey("redis.ip"));
        config.setPost(Integer.valueOf(global.getKey("redis.post")));
        config.setMaxIdle(Integer.valueOf(global.getKey("redis.maxidle")));
        config.setMaxWait(Integer.valueOf(global.getKey("redis.maxwait")));
        config.setTestOnBorrow(Boolean.valueOf(global.getKey("redis.testonborrow")));
        config.setMaxActive(Integer.valueOf(global.getKey("redis.maxactive")));
        config.setPassword(global.getKey("redis.password"));
        RedisClient redisClient = new RedisClient(config);
        return redisClient;
    }
}
