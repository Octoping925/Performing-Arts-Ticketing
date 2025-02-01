package octoping.ticketing.api.controller.arts.request

import java.time.LocalDate

data class ArtInfoResponseDTO(
    val id: Long,
    val name: String,
    val basePrice: Long,
    val discountedPrice: Long,
    val startDate: LocalDate,
    val endDate: LocalDate?,
)
