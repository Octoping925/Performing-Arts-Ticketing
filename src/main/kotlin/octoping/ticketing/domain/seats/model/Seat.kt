package octoping.ticketing.domain.seats.model

class Seat(
    id: Long = 0,
    artId: Long,
    isSoldOut: Boolean,
) {
    private val _id: Long = id
    private val _artId: Long
    private val _isSoldOut: Boolean

    val id: Long
        get() = _id

    val artId: Long
        get() = _artId

    val isSoldOut: Boolean
        get() = _isSoldOut

    init {
        _artId = artId
        _isSoldOut = isSoldOut
    }
}
