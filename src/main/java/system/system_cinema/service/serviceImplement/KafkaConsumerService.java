package system.system_cinema.service.serviceImplement;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import system.system_cinema.service.IKafkaConsumerService;
import java.io.UnsupportedEncodingException;

@Slf4j(topic = "Consumer-service")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConsumerService implements IKafkaConsumerService {

    MailService mailService;

    @Override
    @KafkaListener(topics = "send_mail", groupId = "mail")
    public void consumerTopicSendMail(ConsumerRecord<String, String> record) throws MessagingException, UnsupportedEncodingException {
        log.info("Consumer email start .....................");
        if (record.key().equals("change_password")){
            mailService.sendEmailChangePassword(record.value());
        } else {
            System.out.println(" ");
        }
        log.info("Consumer email end .....................");
    }

//    **************** Su dung khi bai toan co race condition lon: VD 1000 req ******************

//    @KafkaListener(topics = "seat-booking", groupId = "booking-group")
//    public void handleBooking(String message) {
//        try {
//            LockSeatsRequest request = objectMapper.readValue(message, LockSeatsRequest.class);
//            boolean locked = lockSeatAtomic(request.getSeatIds(), request.getShowtimeId(), (long) request.getUserId());
//            String status = locked ? "success" : "fail";
//            redisTemplate.opsForValue().set("booking:" + request.getUserId() + "_" + status, status, Duration.ofMinutes(5));
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean lockSeatAtomic(List<Integer> seatIds, int showtimeId, Long userId) {
//        for (Integer seatId : seatIds) {
//            String key = "seat:" + showtimeId + ":" + seatId;
//            Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(userId), Duration.ofMinutes(5));
//            if (Boolean.FALSE.equals(locked)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
