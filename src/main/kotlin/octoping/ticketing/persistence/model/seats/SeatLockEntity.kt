package octoping.ticketing.persistence.model.seats

import octoping.ticketing.domain.seats.model.SeatLock
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "SeatLock", timeToLive = 7 * 60)
class SeatLockEntity(
    @Id
    val id: String? = null,
    @Indexed
    val userId: Long,
    @Indexed
    val seatId: Long,
) {
    fun toEntity() =
        SeatLock(
            id = id,
            userId = userId,
            seatId = seatId,
        )
}
