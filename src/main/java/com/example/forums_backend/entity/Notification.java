package com.example.forums_backend.entity;

import com.example.forums_backend.entity.my_enum.NotificationStatus;
import com.example.forums_backend.entity.my_enum.NotificationType;
import lombok.*;

import javax.persistence.*;

import static com.example.forums_backend.config.constant.notification.ContentConstant.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "motifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String redirect_url;
    private String content;
    @ManyToOne
    private Account interactive_user;
    @ManyToOne
    private Account receiver;
    private NotificationType type;
    private NotificationStatus status;


    public void setNotificationContent(NotificationType notificationType){
        switch (notificationType){
            case UPVOTE:
                this.content = UPVOTE_POST_CONTENT_NOTIFY;
                break;
            case COMMENT:
                this.content = COMMENT_POST_CONTENT_NOTIFY;
                break;
            case REPLY_COMMENT:
                this.content = REPLY_COMMENT_CONTENT_NOTIFY;
                break;
            case UPVOTE_COMMENT:
                this.content = UPVOTE_COMMENT_CONTENT_NOTIFY;
                break;
            case UNDEFINED:
                break;
        }
    }
}
