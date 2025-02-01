package octoping.ticketing.domain.arts.model

import octoping.ticketing.domain.exception.ValidationException

data class ArtSeat(
    val id: Long = 0,
    val artId: Long,
    val name: String,
    val price: Long,
) {
    init {
        if (price < 0) {
            throw ValidationException("Price cannot be negative: $price")
        }
    }

    fun changePrice(price: Long): ArtSeat {
        if (this.price == price) {
            return this
        }

        return ArtSeat(
            artId = artId,
            name = name,
            price = price,
        )
    }
}
