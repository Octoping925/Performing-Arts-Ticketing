package octoping.ticketing.domain.seats.repository

import octoping.ticketing.domain.seats.model.SeatLock

interface SeatLockRepository {
    fun lockSeat(
        seatId: Long,
        userId: Long,
    )

    fun unlockSeat(seatId: Long)

    fun isLocked(seatId: Long): Boolean

    fun getSeatLock(seatId: Long): SeatLock?
}
