package octoping.ticketing.domain.seats.event

import octoping.ticketing.domain.ticket.model.Ticket

data class SeatPurchaseEvent(
    val ticket: Ticket,
)
