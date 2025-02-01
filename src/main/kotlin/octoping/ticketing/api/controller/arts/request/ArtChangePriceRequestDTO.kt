package octoping.ticketing.api.controller.arts.request

data class ArtChangePriceRequestDTO(
    val artSeatId: Long,
    val basePrice: Long,
    val discountPrice: Long,
)
