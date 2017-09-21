package com.bsmart.application.backend.firmsweb.Repository;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import com.bsmart.application.backend.firmsweb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional

public interface FirmsRepository extends JpaRepository<Firms, Integer> {

    Firms getFirmsById(Integer firms_id);

    //@Query("select f from Firms f where f.id = ?1 and  f.visible = ?2")
    List<Firms> getFirmsByUser_IdAndVisible(Integer id, Boolean visible);

    Firms findByIdAndUser(Integer firm_id, User user);
}