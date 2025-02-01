package octoping.ticketing.api.controller.arts.schema

import java.time.LocalDate

data class ArtItemSchema(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
)
