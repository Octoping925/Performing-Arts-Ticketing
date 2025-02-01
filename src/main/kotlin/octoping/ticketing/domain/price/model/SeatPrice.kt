package octoping.ticketing.domain.price.model

import octoping.ticketing.domain.exception.ValidationException
import java.time.LocalDateTime

private const val MAX_PRICE = 10_000_000_000

/**
 * 좌석 가격 정보. 다만 그냥 가격 정보의 히스토리용.
 */
class SeatPrice(
    id: Long = 0,
    val artId: Long,
    val seatId: Long,
    val price: Long,
    val startDate: LocalDateTime = LocalDateTime.now(),
    endDate: LocalDateTime? = null,
) {
    private var _id: Long
    private var _endDate: LocalDateTime?

    val id get() = _id
    val endDate get() = _endDate

    init {
        if (price < 0) {
            throw ValidationException("Price cannot be negative: $price")
        }

        if (price > MAX_PRICE) {
            throw ValidationException("Price cannot be greater than max price: $price")
        }
        _id = id
        _endDate = endDate
    }

    fun isCurrentlyApplied() = this.endDate != null

    fun endPrice(endDate: LocalDateTime) {
        this._endDate = endDate
    }
}
