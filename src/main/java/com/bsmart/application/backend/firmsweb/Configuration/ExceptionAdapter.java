package com.bsmart.application.backend.firmsweb.Configuration;

import com.bsmart.application.backend.firmsweb.Entity.User;
import com.bsmart.application.backend.firmsweb.Services.MailService;
import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionAdapter {

    public static final String DEFAULT_ERROR_VIEW = "error";
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Value("${app.email.support}")
    private String supportEmail;
    @Value("${app.environment}")
    private String environment;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        User user = userService.getLoggedInUser();
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("user", user);
        mav.addObject("support", supportEmail);
        mav.setViewName(DEFAULT_ERROR_VIEW);

        if (!environment.equals("DEV")) {
            mailService.sendErrorEmail(e, req, user);
        }

        e.printStackTrace();
        return mav;
    }
}
