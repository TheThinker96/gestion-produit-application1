package com.chad.produitmanager.service;

import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.chad.produitmanager.domain.UserSaleAccount}.
 */
public interface UserSaleAccountService {
    /**
     * Save a userSaleAccount.
     *
     * @param userSaleAccountDTO the entity to save.
     * @return the persisted entity.
     */
    UserSaleAccountDTO save(UserSaleAccountDTO userSaleAccountDTO);

    /**
     * Updates a userSaleAccount.
     *
     * @param userSaleAccountDTO the entity to update.
     * @return the persisted entity.
     */
    UserSaleAccountDTO update(UserSaleAccountDTO userSaleAccountDTO);

    /**
     * Partially updates a userSaleAccount.
     *
     * @param userSaleAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserSaleAccountDTO> partialUpdate(UserSaleAccountDTO userSaleAccountDTO);

    /**
     * Get all the userSaleAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSaleAccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userSaleAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSaleAccountDTO> findOne(Long id);

    /**
     * Delete the "id" userSaleAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
