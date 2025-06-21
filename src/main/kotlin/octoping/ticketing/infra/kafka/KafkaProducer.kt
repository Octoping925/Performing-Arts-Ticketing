package octoping.ticketing.infra.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) {
    private val logger = KotlinLogging.logger {}

    fun <T> sendEvent(topic: String, key: String, event: T) {
        logger.info { "카프카 이벤트 전송 시작: 토픽=$topic, 키=$key" }

        kafkaTemplate
            .send(topic, key, event)
            .whenComplete { result, ex ->
                if (ex != null) {
                    logger.error(ex) { "카프카 이벤트 전송 실패: 토픽=$topic, 키=$key" }
                } else {
                    logger.info { "카프카 이벤트 전송 완료: 토픽=$topic, 키=$key" }
                }
            }
    }
}
