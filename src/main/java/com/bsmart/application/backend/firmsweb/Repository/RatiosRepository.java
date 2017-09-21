package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Ratios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatiosRepository extends JpaRepository<Ratios, Integer> {
    List<Ratios> getByYearAndSectorCodeAndTermType(Integer year, String sectorCode, AccountSheetTermType termType);

    Ratios getByYearAndSectorCodeAndCodeAndTermType(Integer year, String sectorCode, String ratioCode, AccountSheetTermType termType);

    Ratios getRatiosByCodeAndTermType(String ratioCode, AccountSheetTermType accountSheetTermType);

}
