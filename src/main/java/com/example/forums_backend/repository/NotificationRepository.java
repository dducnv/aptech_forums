package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationByReceiver_Id(Long id);
}
