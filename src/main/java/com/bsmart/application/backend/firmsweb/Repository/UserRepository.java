package com.bsmart.application.backend.firmsweb.Repository;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Role;
import com.bsmart.application.backend.firmsweb.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findOneByUserName(String name);

    User findOneByEmail(String email);

    User findOneByUserNameOrEmail(String username, String email);

    User findOneByToken(String token);

    User findOneById(Integer id);

    ArrayList<User> findAllByRole(String role);

    @Modifying
    @Transactional
    @Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.userName = ?1")
    int updateLastLogin(String userName);

}
