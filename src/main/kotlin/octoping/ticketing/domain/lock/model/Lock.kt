package octoping.ticketing.domain.lock.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

enum class Lock(
    private val keySupplier: (key: String) -> String,
    private val ttl: Duration
) {
    SEAT_PURCHASE({ "seatPurchase:$it" }, 7.minutes),
    ;

    fun getKey(key: String) = this.keySupplier(key)
    fun getTTL() = this.ttl.toJavaDuration()
}