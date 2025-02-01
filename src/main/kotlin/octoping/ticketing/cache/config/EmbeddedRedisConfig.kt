package octoping.ticketing.cache.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class EmbeddedRedisConfig(
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
}
