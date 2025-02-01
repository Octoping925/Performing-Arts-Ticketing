package octoping.ticketing.persistence.model.seats

import octoping.ticketing.persistence.exception.PersistenceException

class SeatLockException(
    message: String,
) : PersistenceException(message) {
    companion object {
        val ALREADY_LOCKED = SeatLockException("이미 잠겨 있습니다.")
        val MULTIPLE_LOCKS_FOUND = SeatLockException("여러 개의 잠금이 발견되었습니다.")
    }
}
