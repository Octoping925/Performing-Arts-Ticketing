package octoping.ticketing.persistence.model.art

import jakarta.persistence.Column
import jakarta.persistence.Entity
import octoping.ticketing.domain.arts.model.ArtSeat
import octoping.ticketing.persistence.common.BaseEntity

@Entity
class ArtSeatEntity(
    id: Long,
    @Column(name = "art_id", nullable = false, updatable = false)
    val artId: Long,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "price", nullable = false)
    val price: Long,
) : BaseEntity(id) {
    fun toDomain() =
        ArtSeat(
            id = id,
            artId = artId,
            name = name,
            price = price,
        )

    companion object {
        fun from(seat: ArtSeat) =
            ArtSeatEntity(
                id = seat.id,
                artId = seat.artId,
                name = seat.name,
                price = seat.price,
            )
    }
}
