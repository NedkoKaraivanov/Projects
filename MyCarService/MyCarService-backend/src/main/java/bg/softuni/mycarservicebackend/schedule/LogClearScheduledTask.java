package bg.softuni.mycarservicebackend.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class LogClearScheduledTask {

    private final String logFilePath = "user_log.txt";

    @Scheduled(cron = "0 0 0 * * *")
    public void clearLogs() {
        try (FileWriter writer = new FileWriter(logFilePath)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
