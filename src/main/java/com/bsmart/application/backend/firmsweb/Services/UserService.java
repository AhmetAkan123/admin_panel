package com.bsmart.application.backend.firmsweb.Services;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.User;
import com.bsmart.application.backend.firmsweb.FirmsWebBackendApplication;
import com.bsmart.application.backend.firmsweb.Repository.FirmsRepository;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    public final String CURRENT_USER_KEY = "CURRENT_USER";
    @Value("${app.user.verification}")
    private Boolean requireActivation;
    @Value("${app.secret}")
    private String applicationSecret;
    @Autowired
    private UserRepository repo;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private FirmsRepository firmsRepository;

    // Aktivasyon Kontrolcüsü //
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findOneByUserNameOrEmail(username, username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (requireActivation && !user.getToken().equals("1")) {
            FirmsWebBackendApplication.log.error("User [" + username + "] tried to login but is not activated");
            throw new UsernameNotFoundException(username + " has not been activated yet");
        }
        httpSession.setAttribute(CURRENT_USER_KEY, user);
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), auth);
    }

    public void autoLogin(User user) {
        autoLogin(user.getUserName());
    }

    // Kayıt ve Şifre Değiştirme sonrası otomatik giriş //
    public void autoLogin(String username) {
        UserDetails userDetails = this.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    // Üye Kaydı //
    public User register(User user) {
        user.setPassword(encodeUserPassword(user.getPassword()));

        if (this.repo.findOneByUserName(user.getUserName()) == null && this.repo.findOneByEmail(user.getEmail()) == null) {
            System.out.println("seks");
            String activation = createActivationToken(user, false);
            user.setToken(activation);
            // Expiry Date //
            Date expiryDate = user.getRegisterDate();
            Calendar c = Calendar.getInstance();
            c.setTime(expiryDate);
            c.add(Calendar.YEAR, 1);
            expiryDate = c.getTime();
            user.setExpiryDate(expiryDate);
            this.repo.save(user);
            // Kullanıcı için bir adet varsayılan firma oluşturuluyor
            Firms aFirm = new Firms();
            Sectors sectors = sectorRepository.getSectorsById(1);
            aFirm.setFirmname("Varsayılan Firma ");
            aFirm.setFirmmanager(user.getName());
            aFirm.setFirmtelephone("0");
            aFirm.setFirmTaxOffice("Belirtilmemiş");
            aFirm.setFirmwebaddress("http://");
            aFirm.setFirmTexNumber("0");
            aFirm.setSector(sectors);
            aFirm.setFirmaddress("belirtilmemiş");
            aFirm.setVisible(true);
            aFirm.setUser(user);
            this.firmsRepository.save(aFirm);
            return user;
        }

        return null;
    }

    public String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // Üye Sil //
    public Boolean delete(Integer id) {
        this.repo.delete(id);
        return true;
    }

    // Üyeyi aktif et //
    public User activate(String activation) {
        if (activation.equals("1") || activation.length() < 5) {
            return null;
        }
        User u = this.repo.findOneByToken(activation);
        if (u != null) {
            u.setToken("1");
            this.repo.save(u);
            return u;
        }
        return null;
    }

    // Aktivasyon tokenı oluştur //
    public String createActivationToken(User user, Boolean save) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String activationToken = encoder.encodePassword(user.getUserName(), applicationSecret);
        if (save) {
            user.setToken(activationToken);
            this.repo.save(user);
        }
        return activationToken;
    }

    // Şifre yenileme tokenı oluştur //
    public String createResetPasswordToken(User user, Boolean save) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String resetToken = encoder.encodePassword(user.getEmail(), applicationSecret);
        if (save) {
            user.setToken(resetToken);
            this.repo.save(user);
        }
        return resetToken;
    }

    // Aynı aktivasyon için tekrar token oluşturma //
    public User resetActivation(String email) {
        User u = this.repo.findOneByEmail(email);
        if (u != null) {
            createActivationToken(u, true);
            return u;
        }
        return null;
    }

    // Şifre Yenileme //
    public Boolean resetPassword(User user) {
        User u = this.repo.findOneByUserName(user.getUserName());
        if (u != null) {
            u.setPassword(encodeUserPassword(user.getPassword()));
            u.setToken("1");
            this.repo.save(u);
            return true;
        }
        return false;
    }

    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }

    // Giriş yapmış üye bilgileri //
    public User getLoggedInUser(Boolean forceFresh) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) httpSession.getAttribute(CURRENT_USER_KEY);
        if (forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
            user = this.repo.findOneByUserName(userName);
            httpSession.setAttribute(CURRENT_USER_KEY, user);
        }
        return user;
    }

    // Son giriş tarihini güncelle //
    public void updateLastLogin(String userName) {
        this.repo.updateLastLogin(userName);
    }


}
