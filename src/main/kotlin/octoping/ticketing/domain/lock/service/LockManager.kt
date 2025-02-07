package octoping.ticketing.domain.lock.service

import octoping.ticketing.domain.lock.model.Lock
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class LockManager(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun <R> lock(
        lock: Lock,
        key: String,
        block: () -> R,
    ): R {
        try {
            redisTemplate
                .opsForValue()
                .setIfAbsent(lock.getKey(key), "lock", lock.getTTL()) ?: true
            return block()
        } finally {
            redisTemplate.delete(key)
        }
    }

    fun <R> lock(
        lock: Lock,
        key: Long,
        block: () -> R,
    ) = this.lock(lock, key.toString(), block)
}
