package dochigosum.simvex.domain.drawing.service;

import dochigosum.simvex.domain.drawing.presentation.dto.request.PartTransformRequest;
import dochigosum.simvex.domain.drawing.repository.DrawingPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DrawingSaveService {

    private final DrawingPartRepository drawingPartRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PART_KEY_PREFIX = "drawing:parts:";

    // 2분마다 클라이언트가 호출: Redis에 임시 저장
    @Transactional
    public void saveToRedis(List<PartTransformRequest> requests) {
        for (PartTransformRequest req : requests) {
            String key = PART_KEY_PREFIX + req.partId();
            // Redis에 객체 통째로 저장
            redisTemplate.opsForValue().set(key, req);
        }
    }

    // 15분마다 스케줄러가 호출: Redis -> MySQL 대량 저장
    @Transactional
    public void syncRedisToMysql() {
        Set<String> keys = redisTemplate.keys(PART_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {
            PartTransformRequest cachedData = (PartTransformRequest) redisTemplate.opsForValue().get(key);

            if (cachedData != null) {
                // DB의 해당 부품을 찾아서 위치 정보 업데이트
                drawingPartRepository.findById(cachedData.partId()).ifPresent(part -> {
                    part.updateTransform(
                            cachedData.xCoordinate(), cachedData.yCoordinate(), cachedData.zCoordinate(),
                            cachedData.xRotation(), cachedData.yRotation(), cachedData.zRotation()
                    );
                });
                // 동기화 완료 후 Redis 키 삭제
                redisTemplate.delete(key);
            }
        }
    }

}
