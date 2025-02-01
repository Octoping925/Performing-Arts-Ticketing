package octoping.ticketing.domain.price.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import octoping.ticketing.domain.exception.ValidationException

internal class ArtPriceTest : AnnotationSpec() {
    @Test
    fun `ArtPrice는 원 가격이 음수면 예외를 발생한다`() {
        // given
        val price = -100L

        // when & then
        shouldThrow<ValidationException> {
            SeatPrice(
                artId = 0,
                seatId = 1,
                price = price,
            )
        }
    }

    @Test
    fun `ArtPrice는 원 가격이 100억을 넘으면 예외를 발생한다`() {
        // given
        val price = 10_000_000_100L

        // when & then
        shouldThrow<ValidationException> {
            SeatPrice(
                artId = 0,
                seatId = 1,
                price = price,
            )
        }
    }
}
