package octoping.ticketing.persistence.model.seats

import octoping.ticketing.domain.seats.model.Seat
import octoping.ticketing.domain.seats.repository.SeatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SeatRepositoryImpl(
    private val seatJpaRepository: SeatJpaRepository,
) : SeatRepository {
    override fun findById(id: Long): Seat? = seatJpaRepository.findByIdOrNull(id)?.toDomain()

    override fun save(seat: Seat): Seat =
        SeatEntity.from(seat).let {
            seatJpaRepository.save(it).toDomain()
        }
}
