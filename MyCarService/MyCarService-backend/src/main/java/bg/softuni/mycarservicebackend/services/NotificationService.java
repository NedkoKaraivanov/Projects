package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.entities.NotificationEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.repositories.NotificationRepository;
import bg.softuni.mycarservicebackend.util.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    protected NotificationEntity createAdminNotification() {
        List<UserEntity> adminUsers = this.userService.findAdminUsers();

        return NotificationEntity.builder()
                .receivers(adminUsers)
                .isRead(false)
                .time(LocalDateTime.now())
                .message(NotificationMessage.NEW_BOOKING_NOTIFICATION_MESSAGE)
                .build();
    }

    protected NotificationEntity createUserNotification(List<UserEntity> receivers) {
        return NotificationEntity.builder()
                .receivers(receivers)
                .isRead(false)
                .time(LocalDateTime.now())
                .message(NotificationMessage.NEW_BOOKING_STATUS_MESSAGE)
                .build();
    }

    protected NotificationEntity saveNotification(NotificationEntity notification) {
        return this.notificationRepository.save(notification);
    }
}
