package octoping.ticketing.infra.mail

interface EmailService {
    fun sendEmail(mail: Mail, receiverEmail: String)
}

