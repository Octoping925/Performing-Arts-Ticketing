package octoping.ticketing.persistence.model.seats

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatLockRedisRepository : CrudRepository<SeatLockEntity, String> {
    fun existsBySeatId(seatId: Long): Boolean
    fun findBySeatIdAndUserId(seatId: Long, userId: Long): List<SeatLockEntity>
    fun findBySeatId(seatId: Long): List<SeatLockEntity>
}