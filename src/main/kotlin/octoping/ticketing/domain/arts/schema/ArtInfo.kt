package octoping.ticketing.domain.arts.schema

import java.time.LocalDate

data class ArtInfo(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
)
