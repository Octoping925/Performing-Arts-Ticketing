package octoping.ticketing.infra.mail

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("real", "local-real")
@Service
class SesEmailService(
    private val sesConfig: SESConfig,
    private val awsSesEmailService: AmazonSimpleEmailService,
) : EmailService {
    val log = KotlinLogging.logger { }

    override fun sendEmail(mail: Mail, receiverEmail: String) {
        try {
            val request =
                MailUtil.getSendRawEmailRequest(
                    title = mail.title,
                    contentHtml = mail.content,
                    emailSender = sesConfig.emailSender,
                    receiver = receiverEmail,
                )

            awsSesEmailService.sendRawEmail(request)
        } catch (e: Exception) {
            log.error { "이메일 발송 실패" }
            log.error { "Error message: ${e.message}" }
            log.error { e }
            throw e
        }
    }
}
