package octoping.ticketing.domain.seats.service

import octoping.ticketing.domain.exception.NotFoundException
import octoping.ticketing.domain.seats.repository.SeatLockRepository
import octoping.ticketing.domain.seats.repository.SeatRepository
import octoping.ticketing.domain.users.exception.UnauthorizedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeatService(
    private val seatRepository: SeatRepository,
    private val seatLockRepository: SeatLockRepository,
) {
    @Transactional
    fun lockSeat(
        seatId: Long,
        userId: Long,
    ) {
        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

        seatLockRepository.lockSeat(seatId, userId)
    }

    @Transactional
    fun unlockSeat(
        seatId: Long,
        userId: Long,
    ) {
        val lock =
            seatLockRepository.getSeatLock(seatId)
                ?: return

        if (!lock.canUnlock(userId)) {
            throw UnauthorizedException()
        }

        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

        seatLockRepository.unlockSeat(seatId, userId)
    }

    @Transactional
    fun purchaseSeat(
        artId: Long,
        seatId: Long,
        userId: Long,
    ) {
        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")
//        seatRepository.save(seat)
    }
}
