package octoping.ticketing.infra.mail


import com.amazonaws.services.simpleemail.model.RawMessage
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest
import jakarta.mail.Message
import jakarta.mail.MessagingException
import jakarta.mail.Session
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

class MailUtil {
    companion object {
        @Throws(MessagingException::class, IOException::class)
        fun getSendRawEmailRequest(
            title: String,
            contentHtml: String,
            emailSender: String,
            receiver: String,
        ): SendRawEmailRequest {
            val session = Session.getDefaultInstance(Properties())
            val message = MimeMessage(session)

            // Define mail title
            message.subject = title

            // Define mail Sender
            message.setFrom(emailSender)

            // Define mail Receiver
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiver),
            )

            // Create a multipart/alternative child container.
            val msgBody = MimeMultipart("alternative")

            // Create a wrapper for the HTML and text parts.
            val wrap = MimeBodyPart()

            // Define the HTML part.
            val htmlPart = MimeBodyPart()
            htmlPart.setContent(contentHtml, "text/html; charset=UTF-8")

            // Add the text and HTML parts to the child container.
            msgBody.addBodyPart(htmlPart)

            // Add the child container to the wrapper object.
            wrap.setContent(msgBody)

            // Create a multipart/mixed parent container.
            val msg = MimeMultipart("mixed")

            // Add the parent container to the message.
            message.setContent(msg)

            // Add the multipart/alternative part to the message.
            msg.addBodyPart(wrap)

            // Add the attachment to the message.
            val outputStream = ByteArrayOutputStream()
            message.writeTo(outputStream)
            val rawMessage = RawMessage(ByteBuffer.wrap(outputStream.toByteArray()))
            return SendRawEmailRequest(rawMessage)
        }
    }
}
