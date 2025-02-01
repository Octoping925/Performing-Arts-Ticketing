package octoping.ticketing.domain.seats.service

import octoping.ticketing.domain.seats.event.SeatPurchaseEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class SeatEventListener(
    private val seatService: SeatService,
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onSeatPurchase(event: SeatPurchaseEvent) {
        seatService.unlockSeat(event.seatId, event.userId)
    }
}
