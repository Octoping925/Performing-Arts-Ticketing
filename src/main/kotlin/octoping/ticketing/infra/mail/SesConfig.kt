package octoping.ticketing.infra.mail

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("real", "local-real")
@Configuration
class SESConfig {
    @Value("\${aws.ses.accessKey}")
    lateinit var accessKey: String

    @Value("\${aws.ses.secretKey}")
    lateinit var secretKey: String

    @Value("\${email.sender}")
    lateinit var emailSender: String

    @Bean
    fun amazonSimpleEmailService(): AmazonSimpleEmailService {
        val basicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        val awsStaticCredentialsProvider = AWSStaticCredentialsProvider(basicAWSCredentials)

        return AmazonSimpleEmailServiceClientBuilder.standard()
            .withCredentials(awsStaticCredentialsProvider)
            .withRegion("ap-northeast-2")
            .build()
    }
}
