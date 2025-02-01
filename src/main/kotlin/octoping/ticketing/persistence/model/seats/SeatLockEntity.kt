package octoping.ticketing.persistence.model.seats

import octoping.ticketing.domain.seats.model.SeatLock
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "SeatLock", timeToLive = 7 * 60)
class SeatLockEntity(
    @Id
    val id: String? = null,
    val userId: Long,
    val seatId: Long,
) {
    fun toEntity() =
        SeatLock(
            id = id,
            userId = userId,
            seatId = seatId,
        )
}
