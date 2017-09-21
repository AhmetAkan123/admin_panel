package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Enums.AccountSheetTermType;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.VerticalRatios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerticalRatiosRepository extends JpaRepository<VerticalRatios, Integer> {
    List<VerticalRatios> getByCode(String code);

    List<VerticalRatios> getBySectorCode(String sectorCode);

    List<VerticalRatios> getBySectorCodeAndYearAndTermType(String sectorCode, Integer year, AccountSheetTermType termType);

}
