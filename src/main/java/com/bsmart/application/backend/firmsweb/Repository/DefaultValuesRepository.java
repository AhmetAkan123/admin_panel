package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.DefaultValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by SedatKocadogan on 10.7.2017.
 */
@Repository
public interface DefaultValuesRepository extends JpaRepository<DefaultValues, Integer> {

}
