package octoping.ticketing.domain.arts.model

import octoping.ticketing.domain.exception.ValidationException
import java.time.LocalDate

data class ArtDate(
    val startDate: LocalDate,
    val endDate: LocalDate,
) {
    init {
        if (!startDate.isBefore(endDate)) {
            throw ValidationException("시작일은 종료일보다 전이어야 합니다")
        }
    }

    constructor(date: LocalDate) : this(date, date)

    fun isIn(date: LocalDate): Boolean {
        return date in startDate..endDate
    }
}