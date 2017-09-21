package com.bsmart.application.backend.firmsweb.Controllers;

import com.bsmart.application.backend.firmsweb.Entity.User;
import com.bsmart.application.backend.firmsweb.Repository.UserRepository;
import com.bsmart.application.backend.firmsweb.Services.MailService;
import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    protected AuthenticationManager authenticationManager;
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Value("${app.user.verification}")
    private Boolean requireActivation;

    // Üye Giriş Sayfası //
    @RequestMapping("/login")
    public String login(User user) {
        return "user/login";
    }

    // Üye Kayıt Formu//
    @RequestMapping("/user/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    // Üye Kayıt POST //
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerPost(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register";
        }

        User registeredUser = userService.register(user);
        if (registeredUser != null) {
            mailService.sendNewRegistration(user.getEmail(), registeredUser.getToken());
            if (!requireActivation) {
                userService.autoLogin(user.getUserName());
                return "redirect:/";
            }
            return "user/register-success";
        } else {
            log.error("Üye zaten kayıtlı: " + user.getUserName());
            result.rejectValue("email", "error.alreadyExists", "Üye adı veye mail adresi zaten kayıtlı");
            return "user/register";
        }
    }

    // Şifre Yenileme İstek Formu //
    @RequestMapping(value = "/user/reset-password")
    public String resetPasswordEmail(User user) {
        return "user/reset-password";
    }

    // Şifre Yenileme Mail Gönderimi //
    @RequestMapping(value = "/user/reset-password", method = RequestMethod.POST)
    public String resetPasswordEmailPost(User user, BindingResult result) {
        User u = userRepository.findOneByEmail(user.getEmail());
        if (u == null) {
            result.rejectValue("email", "error.doesntExist", "Email adresi bulunamadı");
            return "user/reset-password";
        } else {
            String resetToken = userService.createResetPasswordToken(u, true);
            mailService.sendResetPassword(user.getEmail(), resetToken);
        }

        return "user/reset-password-sent";
    }

    // Şifre Yenileme Ekranı //
    @RequestMapping(value = "/user/reset-password-change")
    public String resetPasswordChange(User user, BindingResult result, Model model) {
        User u = userRepository.findOneByToken(user.getToken());
        if (user.getToken().equals("1") || u == null) {
            result.rejectValue("activation", "error.doesntExist", "Şifre yenileme isteğini bulamadık");
        } else {
            model.addAttribute("userName", u.getUserName());
        }

        return "user/reset-password-change";
    }

    // Şifre Yenileme Post //
    @RequestMapping(value = "/user/reset-password-change", method = RequestMethod.POST)
    public ModelAndView resetPasswordChangePost(User user, BindingResult result) {
        Boolean isChanged = userService.resetPassword(user);
        if (isChanged) {
            userService.autoLogin(user.getUserName());
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("user/reset-password-change", "error", "Password could not be changed");
        }
    }

    // Üyelik Aktivasyonu //
    @RequestMapping("/user/activation-send")
    public ModelAndView activationSend(User user) {
        return new ModelAndView("user/activation-send");
    }


    // Üyelik Aktivasyonu POST //
    @RequestMapping(value = "/user/activation-send", method = RequestMethod.POST)
    public ModelAndView activationSendPost(User user, BindingResult result) {
        User u = userService.resetActivation(user.getEmail());
        if (u != null) {
            mailService.sendNewActivationRequest(u.getEmail(), u.getToken());
            return new ModelAndView("user/activation-sent");
        } else {
            result.rejectValue("email", "error.doesntExist", "Email adresi bulunamadı");
            return new ModelAndView("user/activation-send");
        }
    }

    // Aktivasyon //
    @RequestMapping("/user/activate")
    public String activate(String activation) {
        User u = userService.activate(activation);
        if (u != null) {
            userService.autoLogin(u);
            return "redirect:/";
        }
        return "redirect:/error?message=Aktivasyon kodu geçersiz";
    }

    // AutoLogin //
    @RequestMapping("/user/autologin")
    public String autoLogin(User user) {
        userService.autoLogin(user.getUserName());
        return "redirect:/";
    }

}
