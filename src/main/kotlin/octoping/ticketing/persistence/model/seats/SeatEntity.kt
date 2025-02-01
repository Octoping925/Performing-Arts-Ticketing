package octoping.ticketing.persistence.model.seats

import jakarta.persistence.Column
import jakarta.persistence.Entity
import octoping.ticketing.domain.seats.model.Seat
import octoping.ticketing.persistence.common.BaseEntity

@Entity
class SeatEntity(
    id: Long,
    @Column(name = "art_id", nullable = false, updatable = false)
    val artId: Long,
    @Column(name = "art_seat_id", nullable = false, updatable = false)
    val artSeatId: Long,
    @Column(name = "is_sold_out", nullable = false)
    val isSoldOut: Boolean,
) : BaseEntity(id) {
    fun toDomain() =
        Seat(
            id = id,
            artId = artId,
            artSeatId = artSeatId,
            isSoldOut = isSoldOut,
        )

    companion object {
        fun from(seat: Seat) =
            SeatEntity(
                id = seat.id,
                artId = seat.artId,
                artSeatId = seat.artSeatId,
                isSoldOut = seat.isSoldOut,
            )
    }
}
