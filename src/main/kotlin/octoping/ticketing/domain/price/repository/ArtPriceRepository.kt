package octoping.ticketing.domain.price.repository

import octoping.ticketing.domain.price.model.ArtPrice

interface ArtPriceRepository {
    fun save(artPrice: ArtPrice): ArtPrice
    fun findByArtId(artId: Long): List<ArtPrice>
    fun findRecentByArtId(artId: Long): ArtPrice?
}