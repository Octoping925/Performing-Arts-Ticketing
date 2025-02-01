package octoping.ticketing.persistence.model.art

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtSeatJpaRepository : JpaRepository<ArtSeatEntity, Long>
