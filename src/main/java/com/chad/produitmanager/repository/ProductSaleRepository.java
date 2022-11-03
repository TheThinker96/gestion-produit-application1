package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.ProductSale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductSale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {}
