package octoping.ticketing.domain.lock.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@Component
class LockManager(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun <R> seatPurchaseLock(
        seatId: Long,
        block: () -> R,
    ): R {
        val key = "seatPurchase:$seatId"
        val ttl = 7.minutes

        return lock(key, ttl, block)
    }

    private fun <R> lock(
        key: String,
        ttl: kotlin.time.Duration,
        block: () -> R,
    ): R {
        try {
            redisTemplate
                .opsForValue()
                .setIfAbsent(key, "lock", ttl.toJavaDuration()) ?: true
            return block()
        } finally {
            redisTemplate.delete(key)
        }
    }
}
