package octoping.ticketing.domain.seats.service

import octoping.ticketing.domain.arts.repository.ArtSeatRepository
import octoping.ticketing.domain.discount.model.DiscountCoupon
import octoping.ticketing.domain.discount.model.NoDiscountCoupon
import octoping.ticketing.domain.exception.NotFoundException
import octoping.ticketing.domain.lock.service.LockManager
import octoping.ticketing.domain.seats.event.SeatPurchaseEvent
import octoping.ticketing.domain.seats.exception.SeatSoldOutException
import octoping.ticketing.domain.seats.repository.SeatLockRepository
import octoping.ticketing.domain.seats.repository.SeatRepository
import octoping.ticketing.domain.ticket.model.Ticket
import octoping.ticketing.domain.users.exception.UnauthorizedException
import octoping.ticketing.domain.users.repository.UserRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeatService(
    private val seatRepository: SeatRepository,
    private val artSeatRepository: ArtSeatRepository,
    private val seatLockRepository: SeatLockRepository,
    private val userRepository: UserRepository,
    private val lockManager: LockManager,
    private val eventPublisher: ApplicationEventPublisher,
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
        // Seat의 락은 Redis에 저장하지만, 이를 저장할 때에도 동시성 이슈가 생길 수 있어 분산락 구현
        lockManager.seatPurchaseLock(seatId) {
            val lock =
                seatLockRepository.getSeatLock(seatId)
                    ?: return@seatPurchaseLock

            if (!lock.canUnlock(userId)) {
                throw UnauthorizedException()
            }

            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

            seatLockRepository.unlockSeat(seatId)
        }
    }

    @Transactional
    fun purchaseSeat(
        artId: Long,
        seatId: Long,
        userId: Long,
        discountCoupon: DiscountCoupon = NoDiscountCoupon(),
    ): Ticket {
        validateIsLocked(seatId, userId)

        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

        if (seat.isSoldOut) {
            throw SeatSoldOutException()
        }

        val artSeat =
            artSeatRepository.findById(seat.artSeatId)
                ?: throw NotFoundException("ArtSeat not found")

        val user =
            userRepository.findById(userId)
                ?: throw NotFoundException("User not found")

        val ticket = seat.buyTicket(user, artSeat, discountCoupon)

        seatRepository.save(seat)
        eventPublisher.publishEvent(SeatPurchaseEvent(seat.id, user.id))

        return ticket
    }

    private fun validateIsLocked(
        seatId: Long,
        userId: Long,
    ) {
        val lock =
            seatLockRepository.getSeatLock(seatId)
                ?: throw NotFoundException("Seat not locked")

        if (!lock.canUnlock(userId)) {
            throw UnauthorizedException()
        }
    }
}
