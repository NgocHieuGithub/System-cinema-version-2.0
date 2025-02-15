package system.system_cinema.service;

import jakarta.mail.MessagingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.UnsupportedEncodingException;

public interface IKafkaConsumerService {
    void consumerTopicSendMail(ConsumerRecord<String, String> record) throws MessagingException, UnsupportedEncodingException;
}
