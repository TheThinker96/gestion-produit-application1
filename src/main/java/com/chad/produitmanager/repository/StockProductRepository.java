package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.StockProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StockProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Long> {}
