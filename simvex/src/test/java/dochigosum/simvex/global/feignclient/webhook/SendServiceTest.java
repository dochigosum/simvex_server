package dochigosum.simvex.global.feignclient.webhook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SendServiceTest {

    @Autowired
    private SendService sendService;

    @MockitoBean
    private DiscordWebhookClient discordWebhookClient;

    @Test
    void sendDiscordAlert_Success() throws InterruptedException {
        // When
        sendService.sendDiscordAlert(new Exception(), "GET", "/test");

        // 비동기 실행 대기
        Thread.sleep(500);

        // Then: 가짜 클라이언트의 send 메소드가 호출되었는지 검증
        verify(discordWebhookClient).send(any(DiscordWebhookRequest.class));
    }

    @Test
    void buildMessage() {
        Exception e = new RuntimeException("이것은 테스트 에러 메시지입니다. ".repeat(100)); // 아주 긴 에러

        // MockRequest 생성 (실제 서블릿 객체 대신 가짜 객체 사용)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/api/test");

        // When
        String message = sendService.buildMessage(e, request.getMethod(), request.getRequestURI());

        // Then
        assertThat(message).contains("Internal Server Error");
        assertThat(message).contains("POST");
        assertThat(message.length()).isLessThanOrEqualTo(2000); // 2000자 이하인지 확인
        if (message.length() >= 2000) {
            assertThat(message).endsWith("... (중략)"); // 잘렸을 때 점 세 개로 끝나는지
        }
    }

    @Test
    void buildMessage_shouldTruncate_whenExceedsLimit() {
        // Given: 2000자가 훨씬 넘는 아주 긴 에러 메시지 준비
        String longErrorMessage = "A".repeat(3000);
        Exception e = new RuntimeException(longErrorMessage);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/api/test-limit");

        // When: 메시지 생성
        String result = sendService.buildMessage(e, request.getMethod(), request.getRequestURI());

        // Then: 검증
        assertThat(result.length()).isLessThanOrEqualTo(2000);
        assertThat(result).endsWith("... (중략)");
        assertThat(result).contains("POST");
        assertThat(result).contains("/api/test-limit");

        System.out.println("최종 메시지 길이: " + result.length());

        assertDoesNotThrow(() -> {
            sendService.sendDiscordAlert(e, request.getMethod(), request.getRequestURI());
        });
    }

    @Test
    void asyncTest() {
        // Given
        Exception e = new RuntimeException("비동기 테스트 에러");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/api/async-test");

        // When
        sendService.sendDiscordAlert(e, request.getMethod(), request.getRequestURI());

        // Then
        // 최대 2000ms(2초) 동안 비동기 호출이 일어나는지 기다리며 검증합니다.
        verify(discordWebhookClient, timeout(2000)).send(any(DiscordWebhookRequest.class));
    }
}