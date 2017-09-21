package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.VerticalAnalysisCorrelationValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerticalAnalysisCorrelationValuesRepository extends JpaRepository<VerticalAnalysisCorrelationValues, Integer> {

}
