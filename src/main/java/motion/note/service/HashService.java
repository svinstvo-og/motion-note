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

    private void checkAvailability() {
        Long numberOfHashes = redisTemplate.opsForSet().size("hashes:available");
        if (numberOfHashes == null && numberOfHashes < 200) {
            log.info("Shortage of hashes: {} hashes available", numberOfHashes);
            //TODO kafka event logic
        }
        else {
            log.info("{} hashes available", numberOfHashes);
        }
    }

    public String getHash() {
        checkAvailability();
        String hash = redisTemplate.opsForSet().pop("hashes:available");
        log.info("Hash assigned: {}", hash);
        return hash;
    }
}
