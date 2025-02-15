package system.system_cinema.service;

public interface IKafkaProducerService {
    void SendMessage(String topic, String key, String message);
}
