package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.FinancialRisks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by BSmartPC01 on 10.07.2017.
 */
@Repository
public interface FinancialRisksRepository extends JpaRepository<FinancialRisks, Integer> {
}
