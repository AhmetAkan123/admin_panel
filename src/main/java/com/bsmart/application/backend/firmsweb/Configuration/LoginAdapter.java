package com.bsmart.application.backend.firmsweb.Configuration;

import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

@Configuration
public class LoginAdapter implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        userService.updateLastLogin(event.getAuthentication().getName());
    }

}
