package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import system.system_cinema.service.IKafkaProducerService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducerService implements IKafkaProducerService {

    KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void SendMessage(String topic ,String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }
}
