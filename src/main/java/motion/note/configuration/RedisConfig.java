package motion.note.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();

        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate()
    {
        RedisTemplate<String, String> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}