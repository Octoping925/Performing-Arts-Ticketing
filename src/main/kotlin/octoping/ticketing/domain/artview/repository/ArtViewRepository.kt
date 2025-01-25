package octoping.ticketing.domain.artview.repository

import octoping.ticketing.domain.artview.model.ArtView
import java.time.LocalDateTime

interface ArtViewRepository {
    fun save(artView: ArtView): ArtView

    fun findByUserAndDate(
        userIP: String,
        userId: Long?,
        date: LocalDateTime,
    ): ArtView?

    fun getViewCountByArtBetween(
        artId: Long,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long
}
