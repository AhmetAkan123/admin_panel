package com.bsmart.application.backend.firmsweb.Repository;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.TrendAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendAnalysisRepository extends JpaRepository<TrendAnalysis, Integer> {

}
