package com.bsmart.application.backend.firmsweb.Repository;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.G20Values;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface G20ValuesRepository extends JpaRepository<G20Values, Integer> {
}
