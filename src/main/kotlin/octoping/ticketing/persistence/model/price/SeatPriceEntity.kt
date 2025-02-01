package octoping.ticketing.persistence.model.price

import jakarta.persistence.Column
import jakarta.persistence.Entity
import octoping.ticketing.domain.price.model.SeatPrice
import octoping.ticketing.persistence.common.BaseEntity
import java.time.LocalDateTime

@Entity
class SeatPriceEntity(
    id: Long = 0,
    @Column(name = "art_id", nullable = false)
    var artId: Long,
    @Column(name = "seat_id", nullable = false)
    var seatId: Long,
    @Column(name = "price", nullable = false)
    var price: Long,
    @Column(name = "start_date", nullable = false)
    var startDate: LocalDateTime,
    @Column(name = "end_date")
    var endDate: LocalDateTime?,
) : BaseEntity(id) {
    fun toDomain() =
        SeatPrice(
            id = id,
            artId = artId,
            seatId = seatId,
            price = price,
            startDate = startDate,
            endDate = endDate,
        )

    companion object {
        fun from(artPrice: SeatPrice) =
            SeatPriceEntity(
                id = artPrice.id,
                seatId = artPrice.seatId,
                artId = artPrice.artId,
                price = artPrice.price,
                startDate = artPrice.startDate,
                endDate = artPrice.endDate,
            )
    }
}
