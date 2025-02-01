package octoping.ticketing.domain.arts.service

import octoping.ticketing.api.controller.arts.schema.ArtItemSchema
import octoping.ticketing.domain.arts.repository.ArtRepository
import octoping.ticketing.domain.arts.schema.ArtInfo
import octoping.ticketing.domain.exception.NotFoundException
import octoping.ticketing.domain.price.repository.SeatPriceRepository
import org.springframework.stereotype.Service

@Service
class ArtService(
    private val artRepository: ArtRepository,
    private val seatPriceRepository: SeatPriceRepository,
) {
    fun getInfoById(artId: Long): ArtInfo {
        val art =
            artRepository.findById(artId)
                ?: throw NotFoundException("Art not found. id: $artId")

        return ArtInfo(
            id = art.id,
            name = art.name,
            startDate = art.startDate,
            endDate = art.endDate,
        )
    }

    fun getList(page: Int): List<ArtItemSchema> {
        val arts = artRepository.findAllBy(page)

        return arts.map {
            ArtItemSchema(
                id = it.id,
                name = it.name,
                startDate = it.startDate,
                endDate = it.endDate,
            )
        }
    }
    /*
    TODO: 공연이란, 읽기는 많지만 쓰기는 많지 않은 작업. (공연을 예매하려는 사람은 많아도 하는 사람은 적으니)
     공연 정보가 쉽게 바뀌는 것도 아니니, 캐싱을 적극 활용하자.

     TODO: 시간별 랭킹 같은 경우도 1시간 마다 갱신만 하면 할만할듯. 다만 캐시 스탬피드가 발생할 수 있으니 Jitter를 활용해보자.

     TODO: 분산 캐시 Redis 하나를 두고, 로컬 캐시를 서버마다 두는 방식을 사용한다. 캐시 정보의 정합성 + 가격 변경시 캐시 만료를 위해서 Redis Pub/Sub을 활용해보자.
     */
}
