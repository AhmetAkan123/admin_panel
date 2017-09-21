package com.bsmart.application.backend.firmsweb.Configuration;

import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class ViewInterceptorAdapter extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.getViewName().contains("error")) {
            modelAndView.addObject("g_user", userService.getLoggedInUser());
        }
    }


}
