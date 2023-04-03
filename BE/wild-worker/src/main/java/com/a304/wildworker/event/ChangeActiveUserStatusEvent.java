package com.a304.wildworker.event;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ChangeActiveUserStatusEvent implements DomainEvent {

    private ActiveUser activeUser;
}
