package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.AccountSheets;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetType;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by SedatKocadogan on 2.7.2017.
 */
@Repository
public interface AccountSheetRepository extends JpaRepository<AccountSheets, Integer> {

    List<AccountSheets> getAccountSheetsByTypeAndFirm(AccountSheetType accountSheetType, Firms firms);

    AccountSheets getAccountSheetsByTypeAndTermAndFirmAndYear(AccountSheetType accountSheetType, AccountSheetTermType accountSheetTermType, Firms firms, Integer year);


    @Modifying
    @Transactional
    @Query("delete from AccountSheets ac where ac.id = ?1")
    void removeById(Integer id);
}
