package com.bsmart.application.backend.firmsweb.Repository;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.GrossDomesticProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrossDomesticProductsRepository extends JpaRepository<GrossDomesticProducts, Integer> {
}
