package com.wanan.webgoat.container.lessons;

import com.wanan.webgoat.container.users.WebGoatUser;

public interface Initializeable {
    void initialize(WebGoatUser webGoatUser);
}
