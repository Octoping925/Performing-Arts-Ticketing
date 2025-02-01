package octoping.ticketing.domain.seats.event

data class SeatPurchaseEvent(
    val seatId: Long,
    val userId: Long,
)
