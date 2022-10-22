package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Notification;
import com.example.forums_backend.entity.my_enum.NotificationStatus;
import com.example.forums_backend.entity.my_enum.NotificationType;
import com.example.forums_backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AccountService accountService;

    public List<Notification> getAllNotification(){
        Account account = accountService.getUserInfoData();
        return notificationRepository.findNotificationByReceiver_Id(account.getId());
    }

    public Notification saveNotification(Notification notification){
        Account accountSend = accountService.findByUsername(notification.getInteractive_user().getUsername());
        Account accountReceiver = accountService.findByUsername(notification.getReceiver().getUsername());
        Notification notificationSave = new Notification();
        notificationSave.setInteractive_user(accountSend);
        notificationSave.setReceiver(accountReceiver);
        notificationSave.setRedirect_url(notification.getRedirect_url());
        notificationSave.setType(NotificationType.getNotificationType(notification.getType().getValue()));
        notificationSave.setStatus(NotificationStatus.getNotificationStatus(notification.getType().getValue()));
        notificationSave.setNotificationContent(notification.getType());
        notificationRepository.save(notificationSave);
        return notificationSave;
    }
}
