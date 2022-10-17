package com.example.forums_backend.entity.my_enum;

import java.util.Objects;

public enum NotificationType {
    UPVOTE(1),COMMENT(2),UPVOTE_COMMENT(3),REPLY_COMMENT(4), UNDEFINED(0);
    private final int value;
    NotificationType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static NotificationType getNotificationType(int value){
        for(NotificationType notificationType : NotificationType.values()){
            if(Objects.equals(notificationType.getValue(), value)){
                return notificationType;
            }
        }
        return UNDEFINED;
    }
}
