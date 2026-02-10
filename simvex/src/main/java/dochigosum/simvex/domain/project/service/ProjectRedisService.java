package dochigosum.simvex.domain.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dochigosum.simvex.domain.project.presentation.dto.request.PartStoreRequest;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectRedisService {

    private static final String PART_KEY_PREFIX = "project:parts:";
    private static final Duration TTL = Duration.ofMinutes(30);

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void savePartsToRedis(Long projectId, List<PartStoreRequest> parts) {
        String key = PART_KEY_PREFIX + projectId;
        try {
            String jsonValue = objectMapper.writeValueAsString(parts);
            redisTemplate.opsForValue().set(key, jsonValue, TTL);
            log.info("Parts saved to Redis for project: {}", projectId);
        } catch (JsonProcessingException e) {
            throw new SimvexException(GlobalErrorCode.INVALID_PART_DATA);
        }
    }

    public List<PartStoreRequest> getPartsFromRedis(Long projectId) {
        String key = PART_KEY_PREFIX + projectId;
        String jsonValue = redisTemplate.opsForValue().get(key);

        if (jsonValue == null) {
            return List.of();
        }

        try {
            return objectMapper.readValue(jsonValue,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class,
                                    PartStoreRequest.class));
        } catch (JsonProcessingException e) {
            throw new SimvexException(GlobalErrorCode.INVALID_PART_DATA);
        }
    }
}