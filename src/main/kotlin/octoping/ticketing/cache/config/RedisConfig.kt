package octoping.ticketing.cache.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.port}")
    private val redisPort: Int,
) {
    //    private lateinit var redisServer: RedisServer
//
//    @PostConstruct
//    @Throws(IOException::class)
//    fun redisServer() {
//        redisServer = RedisServer(redisPort)
//        redisServer.start()
//    }
//
//    @PreDestroy
//    fun stopRedis() {
//        redisServer.stop()
//    }
    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val redisTemplate = StringRedisTemplate()

        redisTemplate.apply {
            connectionFactory = redisConnectionFactory
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
        }

        return redisTemplate
    }
}
