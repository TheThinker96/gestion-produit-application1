package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.ProductTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {}
