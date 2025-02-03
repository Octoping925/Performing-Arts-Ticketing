package octoping.ticketing.persistence.model.price

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SeatPriceJpaRepository : JpaRepository<SeatPriceEntity, Long> {
    fun findBySeatId(seatId: Long): List<SeatPriceEntity>

    @Query(
        """
        SELECT sp FROM SeatPriceEntity sp WHERE sp.seatId = :seatId ORDER BY sp.startDate desc LIMIT 1
    """
    )
    fun findRecentBySeatId(seatId: Long): SeatPriceEntity?
}
