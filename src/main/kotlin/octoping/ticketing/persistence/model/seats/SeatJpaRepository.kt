package octoping.ticketing.persistence.model.seats

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatJpaRepository : JpaRepository<SeatEntity, Long>
