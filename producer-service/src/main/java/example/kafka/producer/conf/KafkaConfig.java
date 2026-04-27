package example.kafka.producer.conf;

import example.kafka.common.PaymentEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topics.payment-topic}")
    private String paymentEventsTopic;

    @Bean
    public KafkaTemplate<String, PaymentEvent> getKafkaTemplate (ProducerFactory<String, PaymentEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public NewTopic cretePaymentTopic () {
        return new NewTopic(paymentEventsTopic, 3, (short)1);
    }
}
