package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.StockProductView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StockProductView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockProductViewRepository extends JpaRepository<StockProductView, Long> {}
