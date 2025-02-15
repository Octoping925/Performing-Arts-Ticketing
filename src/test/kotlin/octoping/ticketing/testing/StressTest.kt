package octoping.ticketing.testing

import io.kotest.matchers.shouldBe
import octoping.ticketing.domain.arts.model.Art
import octoping.ticketing.domain.arts.model.ArtSeat
import octoping.ticketing.domain.arts.repository.ArtRepository
import octoping.ticketing.domain.arts.repository.ArtSeatRepository
import octoping.ticketing.domain.price.model.SeatPrice
import octoping.ticketing.domain.price.repository.SeatPriceRepository
import octoping.ticketing.domain.seats.exception.SeatSoldOutException
import octoping.ticketing.domain.seats.model.Seat
import octoping.ticketing.domain.seats.repository.SeatRepository
import octoping.ticketing.domain.seats.service.SeatService
import octoping.ticketing.domain.ticket.repository.TicketRepository
import octoping.ticketing.domain.users.model.User
import octoping.ticketing.domain.users.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class StressTest {
    @Autowired
    private lateinit var ticketRepository: TicketRepository

    @Autowired
    private lateinit var seatService: SeatService

    @Autowired
    private lateinit var seatPriceRepository: SeatPriceRepository

    @Autowired
    private lateinit var artSeatRepository: ArtSeatRepository

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var artRepository: ArtRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Rollback(false)
    @Test
    fun stressTest() {
        // given
        val users = createUser(100000)
        val art = createArt()
        val artSeat = createArtSeat(art)
        val seats = createSeat(art.id, artSeat.id, 1000)
        val seatPrice = createSeatPrice(art, seats[0])

        // when
        // TODO: Thread 돌리면서 20명이 동시에 예매하도록 구현

        val threadCount = 100000
        val executorService = Executors.newFixedThreadPool(20)
        val latch = CountDownLatch(threadCount)

        var isAllSuccess = true
        repeat(threadCount) {
            executorService.submit {
                val user = users[it]
                val seat = seats.random()
                try {
                    seatService.lockSeat(
                        seatId = seat.id,
                        userId = user.id,
                    )

                    // sleep
                    Thread.sleep(3000 + (Math.random() * 1000).toLong())

                    seatService.purchaseSeat(
                        artId = art.id,
                        seatId = seat.id,
                        userId = user.id,
                    )
                } catch (e: SeatSoldOutException) {
                    println("user: ${user.id}, seat: ${seat.id} 예외 발생.." + e.localizedMessage)
                    isAllSuccess = false
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        isAllSuccess shouldBe true
    }

    private fun createSeatPrice(
        art: Art,
        seat: Seat,
    ): SeatPrice =
        seatPriceRepository.save(
            SeatPrice(
                id = 0,
                artId = art.id,
                seatId = seat.id,
                price = 10000,
            ),
        )

    private fun createArtSeat(art: Art): ArtSeat =
        artSeatRepository.save(
            ArtSeat(
                artId = art.id,
                name = "VIP",
                price = 100000,
            ),
        )

    private fun createArt(): Art =
        artRepository.save(
            Art(
                id = 0,
                name = "너무 재밌는 공연",
                description = "너무 재밌는 공연입니다.",
                onePersonBuyLimit = 999,
                startDate = LocalDate.of(2000, 2, 28),
                endDate = LocalDate.of(2100, 12, 31),
            ),
        )

    private fun createUser(range: Int): List<User> =
        (1..range)
            .map {
                User(username = "user-$it")
            }.let { userRepository.saveAll(it) }

    private fun createSeat(
        artId: Long,
        artSeatId: Long,
        quantity: Int,
    ): List<Seat> =
        (1..quantity)
            .map {
                Seat(
                    artId = artId,
                    artSeatId = artSeatId,
                    isSoldOut = false,
                )
            }.let { seatRepository.saveAll(it) }
}
