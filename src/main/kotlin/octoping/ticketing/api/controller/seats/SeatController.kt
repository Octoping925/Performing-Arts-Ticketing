package octoping.ticketing.api.controller.seats

import octoping.ticketing.domain.seats.service.SeatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SeatController(
    private val seatService: SeatService,
) {
    @GetMapping("/arts/{artId}/seats")
    fun getSeats(artId: Long) {
//        seatService.getSeats(artId)
    }

    @PostMapping("/arts/{artId}/seats/{seatId}/reserve")
    fun reserveSeat(
        @PathVariable artId: Long,
        @PathVariable seatId: Long,
    ) {
        /*
         TODO: 사람이 결제 창에 들어간다면 그래도 7분 안에는 결제를 하거나 취소를 할 거란 가설.
          결제창에 들어가면 Redis에 키를 저장하고, 결제가 완료되면 Redis에서 삭제한다. 결제가 너무 오래 걸려 Redis에 키가 없을 경우 빠꾸 먹이고 다시 시도해주세요 라고 띄운다.
         */
        seatService.lockSeat(seatId, 1)
    }

    @PostMapping("/arts/{artId}/seats/{seatId}/purchase")
    fun purchaseSeat(
        @PathVariable artId: Long,
        @PathVariable seatId: Long,
    ) {
        seatService.purchaseSeat(
            artId = artId,
            seatId = seatId,
            userId = 1L,
        )
    }

    @PostMapping("/arts/{artId}/seats/{seatId}/cancel")
    fun cancelSeat(
        @PathVariable artId: Long,
        @PathVariable seatId: Long,
    ) {
        seatService.unlockSeat(seatId, 1)
    }
}
