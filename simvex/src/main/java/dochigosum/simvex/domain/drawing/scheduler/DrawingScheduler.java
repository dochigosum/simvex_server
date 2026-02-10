package dochigosum.simvex.domain.drawing.scheduler;

import dochigosum.simvex.domain.drawing.service.DrawingSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrawingScheduler {

    private final DrawingSaveService drawingSaveService;

    // 15분마다 실행 (밀리초 단위: 15 * 60 * 1000)
    @Scheduled(fixedDelay = 900000)
    public void scheduleMysqlSync() {
        drawingSaveService.syncRedisToMysql();
    }
}