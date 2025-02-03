package octoping.ticketing.persistence.model.price

import octoping.ticketing.domain.price.model.SeatPrice
import octoping.ticketing.domain.price.repository.SeatPriceRepository
import org.springframework.stereotype.Component

@Component
class SeatPriceRepositoryImpl(
    private val seatPriceJpaRepository: SeatPriceJpaRepository,
) : SeatPriceRepository {
    override fun save(artPrice: SeatPrice): SeatPrice =
        seatPriceJpaRepository.save(SeatPriceEntity.from(artPrice)).toDomain()

    override fun findBySeatId(seatId: Long): List<SeatPrice> {
        return seatPriceJpaRepository.findBySeatId(seatId).map { it.toDomain() }
    }

    override fun findRecentBySeatId(seatId: Long): SeatPrice? {
        return seatPriceJpaRepository.findRecentBySeatId(seatId)?.toDomain()
    }
}
