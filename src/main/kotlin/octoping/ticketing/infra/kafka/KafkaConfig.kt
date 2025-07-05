package octoping.ticketing.infra.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff


@Configuration
class KafkaConfig {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun producerFactory(): ProducerFactory<Int, String> = DefaultKafkaProducerFactory(producerConfigs())

    @Bean
    fun producerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        return props
    }

    @Bean
    fun kafkaTemplate() = KafkaTemplate(producerFactory())


    // DLQ 전송용 Recoverer: 실패한 레코드를 "<topic>.DLQ"로 보냄
    @Bean
    fun dlqRecoverer(template: KafkaTemplate<ByteArray?, ByteArray?>) = DeadLetterPublishingRecoverer(
        template,
    ) { record, ex -> TopicPartition(record.topic() + ".DLQ", record.partition()) }


    @Bean
    fun errorHandler(recoverer: DeadLetterPublishingRecoverer): DefaultErrorHandler {
        val handler = DefaultErrorHandler(recoverer, FixedBackOff(1000L, 3L))

        handler.setRetryListeners(
            { record, ex, attempt -> logger.warn { "Attempt $attempt failed for record $record: ${ex.message}" } },
        )

        return handler
    }


    // 컨테이너 팩토리에 ErrorHandler 주입
    @Bean
    fun kafkaListenerContainerFactory(
        cf: ConsumerFactory<ByteArray?, ByteArray?>,
        errorHandler: DefaultErrorHandler,
    ): ConcurrentKafkaListenerContainerFactory<ByteArray?, ByteArray?> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<ByteArray?, ByteArray?>()
        factory.consumerFactory = cf
        factory.setCommonErrorHandler(errorHandler)
        return factory
    }
}
