package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.ProductTransactionView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductTransactionView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTransactionViewRepository extends JpaRepository<ProductTransactionView, Long> {}
