package example.kafka.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(
            topics = "${kafka.topics.payment-topic}", concurrency = "4")
    public void consumePaymentEvents(ConsumerRecord<String, String> message) {

        log.info("Processing message.. key: {} value: {} partition: {} offset: {}", message.key(), message.value(), message.partition(), message.offset());

    }
}
