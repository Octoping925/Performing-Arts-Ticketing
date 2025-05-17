package octoping.ticketing.infra.mail

import octoping.ticketing.domain.notification.NotificationService
import octoping.ticketing.domain.ticket.model.Ticket
import octoping.ticketing.domain.users.service.UserService
import org.springframework.stereotype.Service


@Service
class EmailNotificationService(
    private val emailService: EmailService,
    private val userService: UserService,
) : NotificationService {

    override fun sendBookingConfirmation(details: Ticket) {
        // MimeMessage 생성, 템플릿 처리, details 정보로 메일 내용 구성
        // mailSender.send(mimeMessage);

        val mail = Mail(
            title = "Booking Confirmation",
            content = createPurchaseTicketEmail(details),
        )

        val user = userService.findUserById(details.boughtUserId)

        emailService.sendEmail(mail, user.email)
    }
}
