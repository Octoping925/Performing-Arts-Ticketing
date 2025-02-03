package octoping.ticketing.domain.price.repository

import octoping.ticketing.domain.price.model.SeatPrice

interface SeatPriceRepository {
    fun save(artPrice: SeatPrice): SeatPrice

    fun findBySeatId(seatId: Long): List<SeatPrice>

    fun findRecentBySeatId(seatId: Long): SeatPrice?
}
