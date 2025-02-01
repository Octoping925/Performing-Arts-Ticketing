package octoping.ticketing.domain.seats.model

data class SeatLock(
    val id: String? = null,
    val seatId: Long,
    val userId: Long,
) {
    fun canUnlock(userId: Long) = this.userId == userId
}
