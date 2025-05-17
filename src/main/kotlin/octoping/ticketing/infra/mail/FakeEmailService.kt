package octoping.ticketing.infra.mail

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("local", "test")
@Service
class FakeEmailService : EmailService {
    override fun sendEmail(
        mail: Mail,
        receiverEmail: String,
    ) {
        println("FakeEmailService: title=${mail.title}, content=${mail.content}, receiverEmail=$receiverEmail")
    }
}
