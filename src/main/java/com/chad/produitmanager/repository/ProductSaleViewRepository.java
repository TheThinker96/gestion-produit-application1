package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.ProductSaleView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSaleView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSaleViewRepository extends JpaRepository<ProductSaleView, Long> {}
