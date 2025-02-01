package octoping.ticketing.persistence.model.art

import octoping.ticketing.domain.arts.model.ArtSeat
import octoping.ticketing.domain.arts.repository.ArtSeatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ArtSeatRepositoryImpl(
    private val artSeatJpaRepository: ArtSeatJpaRepository,
) : ArtSeatRepository {
    override fun findById(artSeatId: Long): ArtSeat? = artSeatJpaRepository.findByIdOrNull(artSeatId)?.toDomain()

    override fun save(artSeat: ArtSeat): ArtSeat =
        ArtSeatEntity.from(artSeat).let {
            artSeatJpaRepository.save(it).toDomain()
        }
}
