package octoping.ticketing.domain.price.service

import octoping.ticketing.domain.arts.repository.ArtSeatRepository
import octoping.ticketing.domain.exception.NotFoundException
import octoping.ticketing.domain.price.event.SeatPriceChangedEvent
import octoping.ticketing.domain.price.model.SeatPrice
import octoping.ticketing.domain.price.repository.SeatPriceRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArtPriceService(
    private val artSeatRepository: ArtSeatRepository,
    private val seatPriceRepository: SeatPriceRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun changeArtPrice(
        artId: Long,
        artSeatId: Long,
        price: Long,
        now: LocalDateTime = LocalDateTime.now(),
    ) {
        // TODO: 권한 체크

        val artSeat =
            artSeatRepository.findById(artSeatId)
                ?: throw NotFoundException("ArtSeat not found. id: $artSeatId")

        val newPriceSeat = artSeat.changePrice(price)

        if (newPriceSeat == artSeat) {
            return
        }

        seatPriceRepository.save(
            SeatPrice(
                artId = artId,
                seatId = artSeatId,
                price = price,
                startDate = now,
            ),
        )

        // TODO: publish된 이벤트를 받아서 Redis Pub/Sub을 통해 캐싱 만료시키기
        eventPublisher.publishEvent(
            SeatPriceChangedEvent(newPriceSeat),
        )

        // TODO: 이것도 같이 이벤트 리스너로
        seatPriceRepository.findRecentBySeatId(artSeatId)?.let {
            it.endPrice(now)
            seatPriceRepository.save(it)
        }
    }
}
