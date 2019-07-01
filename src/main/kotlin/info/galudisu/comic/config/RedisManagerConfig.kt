package info.galudisu.comic.config

import org.crazycake.shiro.RedisCacheManager
import org.crazycake.shiro.RedisManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisManagerConfig {

    @Bean
    fun redisManager(): RedisManager {
        val redisManager = RedisManager()
        redisManager.host = "127.0.0.1:6379"
        return redisManager
    }

    @Bean
    fun cacheManager(redisManager: RedisManager): RedisCacheManager {
        val cacheManager = RedisCacheManager()
        cacheManager.redisManager = redisManager
        return cacheManager
    }
}