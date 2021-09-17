
package id.co.bbw.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@ComponentScan("id.co.bbw.config")
@EnableRedisRepositories(basePackages = "id.co.bbw.config")
public class RedisConfig {
    static Logger logger = LoggerFactory.getLogger(RedisConfig.class.getName());

    @Autowired
    Environment env;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        logger.info("jedisConnectionFactory");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        String hostname = env.getProperty("app.redis.hostname","localhost");
        redisStandaloneConfiguration.setHostName(hostname);
        String port = env.getProperty("app.redis.port","6379");
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        redisStandaloneConfiguration.setDatabase(0);
        String password = env.getProperty("app.redis.password","");
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(10));
        jedisClientConfiguration.readTimeout(Duration.ofSeconds(10));

        logger.info("Connecting to Redis");
        if (logger.isInfoEnabled()) {
            logger.info("Hostname:{}", hostname);
            logger.info("Port:{}", port);
            logger.info("Password:{}", password);
        }

        return new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
    }

    /**
     * Jedis Pool is used for SETS. RedisTemplate should be able to do the same but it is to no avail after days of trial and errors.
     * @return JedisPool
     */
    @Bean
    JedisPool jedisPool() {
        logger.info("jedisPool");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(2);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setTestOnBorrow(true);
        final String hostname = env.getProperty("app.redis.hostname","localhost");
        final String portStr = env.getProperty("app.redis.port","6379");
        final int port = Integer.parseInt(portStr);
        final String password = env.getProperty("app.redis.password");

        logger.info("Creating Redis Pool");
        if (logger.isInfoEnabled()) {
            logger.info("Hostname:{}", hostname);
            logger.info("Port:{}", portStr);
            logger.info("Password:{}", password);
        }

        return new JedisPool( new JedisPoolConfig(), hostname, port, 20, password);
    }

    /**
     * The PUBSUB used redisTemplate
     * @return RedisTemplate <String,Object>
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisSerializer stringSerializer() {
        return new GenericToStringSerializer<>(Object.class);
    }

}

