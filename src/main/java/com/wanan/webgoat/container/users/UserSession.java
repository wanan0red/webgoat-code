package com.wanan.webgoat.container.users;

import com.google.common.eventbus.AllowConcurrentEvents;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSession {
    private WebGoatUser webGoatUser;
    @Id
    private String sessionId;
}
