package com.bsmart.application.backend.firmsweb.Controllers;

import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        return "index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        return "403";
    }

}
