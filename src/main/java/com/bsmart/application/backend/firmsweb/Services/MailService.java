package com.bsmart.application.backend.firmsweb.Services;

import com.bsmart.application.backend.firmsweb.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MailService {

    private JavaMailSender mailSender;
    private MailContentBuilder contentBuilder;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.email.support}")
    private String supportEmail;

    @Autowired
    public MailService(JavaMailSender mailSender, MailContentBuilder contentBuilder) {
        this.mailSender = mailSender;
        this.contentBuilder = contentBuilder;
    }

    // Mail Ayarları //
    public void sendMail(String to, String subject, String tokenUrl, String text) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            String content = contentBuilder.build(tokenUrl, text);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e){
            System.err.println(e.getMessage());
        }
    }

    // Şifre Yenileme Maili //
    public void sendResetPassword(String to, String token) {
        String url = appUrl + "/user/reset-password-change?token=" + token;
        String subject = "Şifre Yenileme İşlemi";
        String text = "Şifrenizi yenilemek için lütfen aşağıdaki linke tıklayınız";
        sendMail(to,subject,url,text);
    }

    // Üyelik Aktivasyon Maili//
    public void sendNewRegistration(String to, String token) {
        String url = appUrl + "/user/activate?activation=" + token;
        String subject = "Üyelik Aktivasyonu";
        String text = "Üyeliğinizi aktif etmek için lütfen aşağıdaki linke tıklayınız";
        sendMail(to,subject,url,text);
    }

    // Yeniden Üyelik Aktivasyon Maili //
    public void sendNewActivationRequest(String to, String token) {
        sendNewRegistration(to, token);
    }

    // Hata Maili Gönder -- TEST -- //
    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
        String subject = "Uygulama Hatası : " + req.getRequestURL();
        String text = "Bir hata oluştu: " + e + "\r\nKullanıcı: " + user.getEmail();
        sendMail(supportEmail, subject, text, text);
    }
}
