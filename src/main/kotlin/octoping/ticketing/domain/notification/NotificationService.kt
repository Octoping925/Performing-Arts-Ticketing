package octoping.ticketing.domain.notification

import octoping.ticketing.domain.ticket.model.Ticket

interface NotificationService {
    fun sendBookingConfirmation(details: Ticket)
}
