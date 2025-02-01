package octoping.ticketing.persistence.model.seats

import octoping.ticketing.domain.exception.DomainException

class SeatLockException(
    message: String,
) : DomainException(message) {
    companion object {
        val ALREADY_LOCKED = SeatLockException("이미 잠겨 있습니다.")
        val MULTIPLE_LOCKS_FOUND = SeatLockException("여러 개의 잠금이 발견되었습니다.")
        val TOO_LATE = SeatLockException("시간이 초과되었습니다. 다시 시도해주세요.")
    }
}
