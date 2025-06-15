package octoping.ticketing.domain.notification

import octoping.ticketing.domain.seats.event.SeatPurchaseEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val notificationService: NotificationService,
) {
    @KafkaListener(
        topics = ["booking-topic"],
        groupId = "notification-group",
    )
    fun onSeatPurchaseNotification(event: SeatPurchaseEvent) {
        notificationService.sendBookingConfirmation(event.ticket)
    }
}
