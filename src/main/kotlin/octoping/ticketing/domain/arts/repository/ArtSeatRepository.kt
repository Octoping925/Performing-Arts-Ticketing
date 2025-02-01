package octoping.ticketing.domain.arts.repository

import octoping.ticketing.domain.arts.model.ArtSeat

interface ArtSeatRepository {
    fun findById(artSeatId: Long): ArtSeat?

    fun save(artSeat: ArtSeat): ArtSeat
}
