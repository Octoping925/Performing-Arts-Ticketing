package octoping.ticketing.api.controller.arts.request

data class ArtChangePriceRequestDTO(
    val basePrice: Long,
    val discountPrice: Long,
)
