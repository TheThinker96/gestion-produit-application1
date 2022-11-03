package com.chad.produitmanager.repository;

import com.chad.produitmanager.domain.UserSaleAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserSaleAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSaleAccountRepository extends JpaRepository<UserSaleAccount, Long> {}
