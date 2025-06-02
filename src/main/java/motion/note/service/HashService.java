package motion.note.service;

import lombok.extern.slf4j.Slf4j;
import motion.note.model.Link;
import motion.note.model.Note;
import motion.note.repository.LinkWriteRepository;
import motion.note.service.event.HashRefillEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
public class HashService {
    private final RedisTemplate<String, String> redisTemplate;
    private final LinkWriteRepository linkWriteRepository;

    final HashRefillEventPublisher hashRefillEventPublisher;

    public HashService(RedisTemplate<String, String> redisTemplate, LinkWriteRepository linkWriteRepository, HashRefillEventPublisher hashRefillEventPublisher) {
        this.redisTemplate = redisTemplate;
        this.linkWriteRepository = linkWriteRepository;
        this.hashRefillEventPublisher = hashRefillEventPublisher;
    }

    private boolean hashesAvaliable() {
        int minThreshold = 200;

        Long numberOfHashes = redisTemplate.opsForSet().size("hashes:available");
        if (numberOfHashes == null || numberOfHashes == 0) {
            log.warn("No hashes available. sending refill request");
            hashRefillEventPublisher.publishRefillRequest();
            return false;
        }
        else if (numberOfHashes < minThreshold) {
            log.info("Shortage of hashes: {} hashes available, sending refill request", numberOfHashes);
            hashRefillEventPublisher.publishRefillRequest();
        }
        else {
            log.info("{} hashes available", numberOfHashes);
        }
        return true;
    }

    private String getHash() throws InterruptedException {
        String hash;
        if (!hashesAvaliable()) {
            for (int i=0; i < 10; i++) {
                Thread.sleep(1000);
                hash = redisTemplate.opsForSet().pop("hashes:available");
                if (hash != null) {
                    break;
                }
                log.warn("Retry #{} failed, no hashes available", i);
            }
            log.error("Hash generation failed");
            throw new RuntimeException("Hash generation failed");
        }
        hash = redisTemplate.opsForSet().pop("hashes:available");
        log.info("Hash assigned: {}", hash);
        return hash;
    }

    @Transactional(readOnly = false)
    public void createLink(Timestamp validUntil, Note note) throws InterruptedException {
        String hash = getHash();

        Link link = new Link();
        link.setValidUntil(validUntil);
        link.setCreatedAt(note.getCreatedAt());
        link.setNote(note);
        link.setToken(hash);

        try {linkWriteRepository.save(link);}
        catch (Exception e) {
            log.error("Failed to save link", e);
            throw new RuntimeException("Failed to save link");
        }
        log.info("Created link to note: {}, id = {}", link.getNote().getName(), link.getNote().getNoteId());
    }
}















