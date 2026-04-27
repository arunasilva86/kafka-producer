package example.kafka.producer.service;

import example.kafka.common.PaymentEvent;
import example.kafka.producer.util.PaymentUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PaymentProducerService {

    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    private String paymentEventsTopic;

    public PaymentProducerService(
            KafkaTemplate<String, PaymentEvent> kafkaTemplate,
            @Value("${kafka.topics.payment-topic}") String paymentEventsTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentEventsTopic = paymentEventsTopic;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessageAsynchronously() {

        PaymentEvent paymentEvent = PaymentUtility.generateRandomTransaction();
        kafkaTemplate.send(paymentEventsTopic, UUID.randomUUID().toString(), paymentEvent)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        onSuccess(result);
                    } else {
                        onFailure(throwable);
                    }
                });

    }

    private void onFailure(Throwable throwable) {
        log.error("Failed sending message to kafka");

    }

    private void onSuccess(SendResult<String, PaymentEvent> result) {

        log.info("Received new metadata. \n" +
                        "Topic: {}, Partition: {}, Offset: {}, Timestamp: {}",
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().timestamp());
    }


}
