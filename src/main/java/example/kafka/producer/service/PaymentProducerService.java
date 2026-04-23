package example.kafka.producer.service;

import example.kafka.producer.util.PaymentUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;

    private String paymentEventsTopic;

    public PaymentProducerService(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${kafka.topics.payment-topic}") String paymentEventsTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentEventsTopic = paymentEventsTopic;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessageAsynchronously() {

        String paymentEvent = PaymentUtility.generateRandomTransaction();
        kafkaTemplate.send(paymentEventsTopic, null, paymentEvent)
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

    private void onSuccess(SendResult<String, String> result) {

        log.info("Received new metadata. \n" +
                        "Topic: {}, Partition: {}, Offset: {}, Timestamp: {}",
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().timestamp());
    }


}
