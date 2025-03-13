package system.system_cinema.service.serviceImplement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import system.system_cinema.service.IMailService;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j(topic = "Mail-service-logging")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailService implements IMailService {
    JavaMailSender mailSender;

    @NonFinal
    @Value("${spring.mail.from}")
    String emailFrom;

    /**
     * Send email by Google SMTP
     */
    @Override
    public void sendEmailChangePassword(String recipients) throws MessagingException, UnsupportedEncodingException {
        log.info("Email change password is sending ...");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom, "KHT Cinema System");

        if (recipients.contains(",")) { // send to multiple users
            helper.setTo(InternetAddress.parse(recipients));
        } else { // send to single user
            helper.setTo(recipients);
        }

        String SUBJECT = "Xác minh Email";
        helper.setSubject(SUBJECT);
        String code = generateRandomNumber();
        String CONTENT = "Xin chào,\n" +
                "Mã OTP của bạn là: " + code +".\n" +
                "Mã OTP sẽ hết hạn sau 5 phút.\n" +
                "Nếu bạn không yêu cầu , vui lòng bỏ qua email này.\n" +
                "Trân trọng, " + "Hệ thống đặt vé xem phim HKT";
        helper.setText(CONTENT, true);
        mailSender.send(message);

        log.info("Email change password has sent to successfully, recipients: {}", recipients);
    }

    @Override
    public void sendEmailOrderTicket(String recipients) throws MessagingException, UnsupportedEncodingException {
        log.info("Email order ticket is sending ...");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom, "KHT Cinema System");

        if (recipients.contains(",")) { // send to multiple users
            helper.setTo(InternetAddress.parse(recipients));
        } else { // send to single user
            helper.setTo(recipients);
        }

        String SUBJECT = "Xác minh Email";
        helper.setSubject(SUBJECT);
        String code = generateRandomNumber();
        String CONTENT = "Xin chào,\n" +
                "Cảm ơn bạn đã order: " + code +".\n" +
                "Trân trọng, " + "Hệ thống đặt vé xem phim HKT";
        helper.setText(CONTENT, true);
        mailSender.send(message);

        log.info("Email order ticket has sent to successfully, recipients: {}", recipients);
    }

    private  String generateRandomNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
