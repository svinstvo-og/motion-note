package motion.note.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HashRefillEventPublisher {
    private static final String TOPIC = "refill-hashes";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public HashRefillEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreatedEvent() {
        log.info("Sending hash refill request");
        kafkaTemplate.send(TOPIC, "");
    }
}