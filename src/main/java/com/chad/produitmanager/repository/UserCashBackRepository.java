package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.UserCashBack;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserCashBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserCashBackRepository extends JpaRepository<UserCashBack, Long> {}
