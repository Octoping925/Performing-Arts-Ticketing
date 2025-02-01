package octoping.ticketing.domain.ticket.service

import octoping.ticketing.domain.arts.repository.ArtRepository

class TicketService(
    private val artRepository: ArtRepository,
) {
    fun refundTicket(
        ticketId: Long,
        userId: Long,
    ) {
        TODO()
    }
}
