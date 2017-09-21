package com.bsmart.application.backend.firmsweb.Services;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.User;
import com.bsmart.application.backend.firmsweb.Repository.FirmsRepository;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FirmsRepository firmsRepository;
    @Autowired
    SectorRepository sectorRepository;

    // Firma Güncelleme //
    public Firms updateFirm(Firms firm, Integer user_id) {

        User selectedUser = userRepository.findOne(user_id);
        User existingUser = firm.getUser();

        existingUser.setId(selectedUser.getId());
        existingUser.setPassword(selectedUser.getPassword());
        existingUser.setName(selectedUser.getName());
        existingUser.setEmail(selectedUser.getEmail());
        existingUser.setUserName(selectedUser.getUserName());
        existingUser.setRegisterDate(selectedUser.getRegisterDate());
        existingUser.setToken(selectedUser.getToken());
        existingUser.setExpiryDate(selectedUser.getExpiryDate());
        existingUser.setLastLogin(selectedUser.getLastLogin());
        existingUser.setRole(selectedUser.getRole());
        existingUser.setLocked(selectedUser.isLocked());
        existingUser.setFirmCount(selectedUser.getFirmCount());
        existingUser.setTelephone(selectedUser.getTelephone());
        existingUser.setAddress(selectedUser.getAddress());

        this.firmsRepository.save(firm);
        return firm;
    }

    // Admin paneli üzerinden üye kaydı //
    public User register(User user) {
        user.setPassword(encodeUserPassword(user.getPassword()));

        if (this.userRepository.findOneByUserName(user.getUserName()) == null && this.userRepository.findOneByEmail(user.getEmail()) == null) {
            user.setToken("1");
            // Expiry Date //
            Date expiryDate = user.getRegisterDate();
            Calendar c = Calendar.getInstance();
            c.setTime(expiryDate);
            c.add(Calendar.YEAR, 1);
            expiryDate = c.getTime();
            user.setExpiryDate(expiryDate);
            this.userRepository.save(user);
            // //
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

    // Admin paneli üzerinden üye güncelleme //
    public User update(User user) {

        User existingUser = userRepository.findOne(user.getId());
        // Formdan Gelen Şifre Boş İse Eski Şifre Kullanılmaya Devam Ediliyor //
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(existingUser.getPassword());
        } else {
            // Formdaki Şifre Farklıysa Encode Et ve Kaydet //
            user.setPassword(encodeUserPassword(user.getPassword()));
        }

        user.setToken(existingUser.getToken());
        user.setRegisterDate(existingUser.getRegisterDate());
        user.setExpiryDate(existingUser.getExpiryDate());
        user.setLastLogin(existingUser.getLastLogin());
        user.setUserFirms(existingUser.getUserFirms());
        this.userRepository.save(user);
        return user;
    }

    public String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


}
