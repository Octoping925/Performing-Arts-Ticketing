package octoping.ticketing.domain.seats.service

import octoping.ticketing.domain.arts.repository.ArtRepository
import octoping.ticketing.domain.arts.repository.ArtSeatRepository
import octoping.ticketing.domain.discount.model.DiscountCoupon
import octoping.ticketing.domain.exception.NotFoundException
import octoping.ticketing.domain.price.model.SeatPrice
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
    private val artRepository: ArtRepository,
    private val userRepository: UserRepository,
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
        val lock =
            seatLockRepository.getSeatLock(seatId)
                ?: return

        if (!lock.canUnlock(userId)) {
            throw UnauthorizedException()
        }

        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

        seatLockRepository.unlockSeat(seatId)
    }

    @Transactional
    fun purchaseSeat(
        artId: Long,
        seatId: Long,
        userId: Long,
        price: SeatPrice,
        discountCoupon: DiscountCoupon,
    ): Ticket {
        validateIsLocked(seatId, userId)

        val seat =
            seatRepository.findById(seatId)
                ?: throw NotFoundException("Seat not found")

        if (seat.isSoldOut) {
            throw SeatSoldOutException()
        }

        val art =
            artRepository.findById(artId)
                ?: throw NotFoundException("Art not found")

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
