package octoping.ticketing.persistence.model.ticket

import jakarta.persistence.Column
import jakarta.persistence.Entity
import octoping.ticketing.domain.ticket.model.Ticket
import octoping.ticketing.persistence.common.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

@Entity
class TicketEntity(
    id: Long = 0,
    @Column(name = "reservation_id", nullable = false, updatable = false)
    var reservationId: UUID,
    @Column(name = "art_id")
    var artId: Long,
    @Column(name = "original_price")
    var originalPrice: Long,
    @Column(name = "bought_price")
    var boughtPrice: Long,
    @Column(name = "bought_user_id")
    var boughtUserId: Long,
    @Column(name = "seat_id", nullable = false, updatable = false)
    var seatId: Long,
    @Column(name = "is_refunded")
    var isRefunded: Boolean = false,
    @Column(name = "bought_at")
    var boughtAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "refunded_at")
    var refundedAt: LocalDateTime? = null,
) : BaseEntity(id) {
    fun toTicket() =
        Ticket(
            id,
            reservationId,
            artId,
            originalPrice,
            boughtPrice,
            boughtUserId,
            seatId,
            isRefunded,
            boughtAt,
            refundedAt,
        )

    companion object {
        fun from(ticket: Ticket) =
            TicketEntity(
                ticket.id,
                ticket.reservationId,
                ticket.artId,
                ticket.originalPrice,
                ticket.boughtPrice,
                ticket.boughtUserId,
                ticket.seatId,
                ticket.isRefunded,
                ticket.boughtAt,
                ticket.refundedAt,
            )
    }
}
