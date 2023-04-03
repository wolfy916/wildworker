package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.ChangeActiveUserStatusEvent;
import com.a304.wildworker.event.SetCoolTimeEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeActiveUserStatusEventHandler {

    @EventPublish
    @EventListener
    public void setOrResetCoolTime(ChangeActiveUserStatusEvent event) {
        ActiveUser activeUser = event.getActiveUser();
        if (activeUser.canMatching()) {
            if (activeUser.getCoolTime() == null) {
                Events.raise(SetCoolTimeEvent.of(activeUser));
            }
        } else {
            activeUser.resetCoolTime();
        }
    }
}
