package octoping.ticketing.domain.price.event

import octoping.ticketing.domain.arts.model.ArtSeat

data class SeatPriceChangedEvent(
    val price: ArtSeat,
)
