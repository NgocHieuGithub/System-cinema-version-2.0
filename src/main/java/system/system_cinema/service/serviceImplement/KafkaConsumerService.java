package system.system_cinema.service.serviceImplement;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import system.system_cinema.service.IKafkaConsumerService;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConsumerService implements IKafkaConsumerService {

    MailService mailService;

    @Override
    @KafkaListener(topics = "send_mail", groupId = "mail")
    public void consumerTopicSendMail(ConsumerRecord<String, String> record) throws MessagingException, UnsupportedEncodingException {
        if (record.key().equals("change_password")){
            mailService.sendEmailChangePassword(record.value());
        } else {
            System.out.println(" ");
        }
    }

}
