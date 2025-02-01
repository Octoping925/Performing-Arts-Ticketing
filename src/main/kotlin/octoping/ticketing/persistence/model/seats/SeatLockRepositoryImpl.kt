package octoping.ticketing.persistence.model.seats

import octoping.ticketing.domain.seats.model.SeatLock
import octoping.ticketing.domain.seats.repository.SeatLockRepository
import org.springframework.stereotype.Component

@Component
class SeatLockRepositoryImpl(
    private val seatLockRedisRepository: SeatLockRedisRepository,
) : SeatLockRepository {
    override fun getSeatLock(seatId: Long): SeatLock? {
        val seatLocks = seatLockRedisRepository.findBySeatId(seatId)

        if (seatLocks.isEmpty()) {
            return null
        }

        if (seatLocks.size > 1) {
            throw SeatLockException.MULTIPLE_LOCKS_FOUND
        }

        return seatLocks[0].toEntity()
    }

    override fun lockSeat(
        seatId: Long,
        userId: Long,
    ) {
        val seatLocks = seatLockRedisRepository.findBySeatId(seatId)

        if (seatLocks.isNotEmpty()) {
            throw SeatLockException.ALREADY_LOCKED
        }

        val seatLock =
            SeatLockEntity(
                seatId = seatId,
                userId = userId,
            )

        seatLockRedisRepository.save(seatLock)
    }

    override fun unlockSeat(seatId: Long) {
        val seatLocks = seatLockRedisRepository.findBySeatId(seatId)

        if (seatLocks.isEmpty()) {
            return
        }

        if (seatLocks.size > 1) {
            throw SeatLockException.MULTIPLE_LOCKS_FOUND
        }

        seatLockRedisRepository.delete(seatLocks[0])
    }

    override fun isLocked(seatId: Long) = seatLockRedisRepository.existsBySeatId(seatId)
}
