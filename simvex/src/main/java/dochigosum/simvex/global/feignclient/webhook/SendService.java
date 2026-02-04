package dochigosum.simvex.global.feignclient.webhook;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendService {

    private final DiscordWebhookClient discordWebhookClient;

    public void sendDiscordAlert(Exception e, HttpServletRequest request) {
        try {
            String message = buildMessage(e, request);
            discordWebhookClient.send(
                    new DiscordWebhookRequest(message)
            );
        } catch (Exception ex) {
            log.error("Failed to send discord webhook", ex);
        }
    }

    public String buildMessage(Exception e, HttpServletRequest request) {
        String errorMessage = """
            ## Internal Server Error
            - Method: `%s`
            - URI: `%s`
            - %s
            """.formatted(request.getMethod(), request.getRequestURI(), e.getMessage());

        // 2000자 이상이면 자름
        if (errorMessage.length() > 2000) {
            return errorMessage.substring(0, 1990) + "\n... (중략)";
        }
        return """
            ## Internal Server Error
            - Method: `%s`
            - URI: `%s`
            - %s
            """.formatted(request.getMethod(), request.getRequestURI(), e.getMessage());
    }
}
