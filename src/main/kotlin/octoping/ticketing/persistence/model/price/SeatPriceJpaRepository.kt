package octoping.ticketing.persistence.model.price

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatPriceJpaRepository : JpaRepository<SeatPriceEntity, Long>
