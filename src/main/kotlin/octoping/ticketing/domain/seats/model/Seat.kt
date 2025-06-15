package octoping.ticketing.domain.seats.model

import octoping.ticketing.domain.arts.model.ArtSeat
import octoping.ticketing.domain.discount.model.DiscountCoupon
import octoping.ticketing.domain.discount.model.NoDiscountCoupon
import octoping.ticketing.domain.exception.ValidationException
import octoping.ticketing.domain.ticket.model.Ticket
import octoping.ticketing.domain.users.model.User

class Seat(
    id: Long = 0,
    artId: Long,
    artSeatId: Long,
    isSoldOut: Boolean,
) {
    private val _id: Long = id
    private val _artId: Long = artId
    private val _artSeatId: Long = artSeatId
    private var _isSoldOut: Boolean = isSoldOut

    val id: Long
        get() = _id

    val artId: Long
        get() = _artId

    val artSeatId: Long
        get() = _artSeatId

    val isSoldOut: Boolean
        get() = _isSoldOut

    fun buyTicket(user: User, artSeat: ArtSeat, discountCoupon: DiscountCoupon = NoDiscountCoupon()): Ticket {
        if (user.isNew()) {
            throw ValidationException("저장되지 않은 유저입니다")
        }

        if (artSeat.id != this._artSeatId) {
            throw ValidationException("좌석 정보가 일치하지 않습니다")
        }

        _isSoldOut = true

        return Ticket(
            artId = this.id,
            originalPrice = artSeat.price,
            boughtPrice = discountCoupon.discount(artSeat.price),
            boughtUserId = user.id,
            reservationId = TODO(),
            seatId = TODO(),
        )
    }
}
