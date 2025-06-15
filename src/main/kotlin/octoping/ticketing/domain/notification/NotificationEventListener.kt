package octoping.ticketing.domain.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import octoping.ticketing.domain.seats.event.SeatPurchaseEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val notificationService: NotificationService,
) {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        topics = ["booking-topic"],
        groupId = "notification-group",
    )
    fun onSeatPurchaseNotification(event: SeatPurchaseEvent) {
        logger.info { "티켓 구매 이벤트 수신: 예약 ID=${event.ticket.reservationId}, 공연 ID=${event.ticket.artId}, 좌석 ID=${event.ticket.seatId}" }

        notificationService.sendBookingConfirmation(event.ticket)
        logger.info { "티켓 구매 확인 알림 전송 완료: 예약 ID=${event.ticket.reservationId}" }
    }
}
