package example.kafka.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(
            topics = "${kafka.topics.payment-topic}", concurrency = "4")
    public void consumePaymentEvents(ConsumerRecord<String, String> message, Acknowledgment ack) {

        log.info("Processing message.. key: {} value: {} partition: {} offset: {}", message.key(), message.value(), message.partition(), message.offset());

        ack.acknowledge(); // default behavior is auto commit (configurable in application.yml) But for at least one delivery manual commit like this is required

    }
}
