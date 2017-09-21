package com.bsmart.application.backend.firmsweb.Entity;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Role;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "firmsusers")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    @Email(message = "Email adresi geçerli değil")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 255)
    private String password;

    @Transient
    private String confirmPassword;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Lütfen Kullanıcı Adını Yazınız")
    private String userName;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date registerDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expiryDate;

    private String token;

    private String lastLogin;

    @NotNull
    @Size(max = 255)
    private String role = Role.ROLE_METAL;

    private boolean locked = false;

    private int firmCount = 1;
    private String telephone = "0";
    private String address = "";

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Firms> userFirms;

    public User() {

    }

    public User(String email, String password, String name, String role, boolean locked, int firmCount) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.locked = locked;
        this.firmCount = firmCount;

    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Firms> getUserFirms() {
        return userFirms;
    }

    public void setUserFirms(List<Firms> userFirms) {
        if (this.userFirms == null) {
            this.userFirms = userFirms;
        } else {
            this.userFirms.retainAll(userFirms);
            this.userFirms.addAll(userFirms);
        }
    }

    public int getFirmCount() {
        return firmCount;
    }

    public void setFirmCount(int firmCount) {
        this.firmCount = firmCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {

        switch (role) {
            case Role.ROLE_METAL:
                this.setFirmCount(1);
                break;
            case Role.ROLE_BRONZE:
                this.setFirmCount(5);
                break;
            case Role.ROLE_SILVER:
                this.setFirmCount(10);
                break;
            case Role.ROLE_GOLD:
                this.setFirmCount(50);
                break;
            case Role.ROLE_SUPER_ADMIN:
                this.setFirmCount(-1);
                break;
            default:
                this.setFirmCount(1);
                break;
        }
        this.role = role;
    }

    public String getRoleHumanized() {
        switch (role) {
            case Role.ROLE_METAL:
                return "Temel Üyelik";
            case Role.ROLE_BRONZE:
                return "Bronze Üyelik";
            case Role.ROLE_SILVER:
                return "Gümüş Üyelik";
            case Role.ROLE_GOLD:
                return "Altın Üyelik";
            case Role.ROLE_SUPER_ADMIN:
                return "GOD MODE...";
            default:
                return "Belirtilmemiş";
        }
    }

    public String getTokenHumanized() {
        switch (token) {
            case "1":
                return "Aktif";
            default:
                return "Pasif";
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Boolean isAdmin() {
        return this.role.equals(Role.ROLE_SUPER_ADMIN);
    }

    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }

    public int getTotalFirmCount() {
        int firmCount = userFirms.size();
        return firmCount;
    }
}