package octoping.ticketing.domain.seats.service

import io.github.oshai.kotlinlogging.KotlinLogging
import octoping.ticketing.domain.seats.event.SeatPurchaseEvent
import octoping.ticketing.infra.kafka.KafkaProducer
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class SeatEventListener(
    private val seatService: SeatService,
    private val kafkaProducer: KafkaProducer,
) {
    private val logger = KotlinLogging.logger {}

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onSeatPurchase(event: SeatPurchaseEvent) {
        seatService.unlockSeat(event.ticket.seatId, event.ticket.boughtUserId)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onSeatPurchaseNotification(event: SeatPurchaseEvent) {
        logger.info { "티켓 구매 이벤트 카프카 전송 시작: 예약 ID=${event.ticket.reservationId}" }
        kafkaProducer.sendEvent("booking-topic", event.ticket.reservationId.toString(), event)
    }
}
