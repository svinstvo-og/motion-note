package motion.note.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HashService {
    private final RedisTemplate<String, String> redisTemplate;

    public HashService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private boolean hashesAvaliable() {
        int minThreshold = 200;

        Long numberOfHashes = redisTemplate.opsForSet().size("hashes:available");
        if (numberOfHashes == null) {
            log.warn("No hashes available. sending refill request");
            return false;
        }
        else if (numberOfHashes < minThreshold) {
            log.info("Shortage of hashes: {} hashes available, sending refill request", numberOfHashes);
            //TODO kafka event logic
        }
        else {
            log.info("{} hashes available", numberOfHashes);
        }
        return true;
    }

    public String getHash() throws InterruptedException {
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
}
