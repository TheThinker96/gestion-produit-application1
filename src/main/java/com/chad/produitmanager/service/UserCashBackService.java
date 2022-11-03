package com.chad.produitmanager.service;

import com.chad.produitmanager.service.dto.UserCashBackDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.chad.produitmanager.domain.UserCashBack}.
 */
public interface UserCashBackService {
    /**
     * Save a userCashBack.
     *
     * @param userCashBackDTO the entity to save.
     * @return the persisted entity.
     */
    UserCashBackDTO save(UserCashBackDTO userCashBackDTO);

    /**
     * Updates a userCashBack.
     *
     * @param userCashBackDTO the entity to update.
     * @return the persisted entity.
     */
    UserCashBackDTO update(UserCashBackDTO userCashBackDTO);

    /**
     * Partially updates a userCashBack.
     *
     * @param userCashBackDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserCashBackDTO> partialUpdate(UserCashBackDTO userCashBackDTO);

    /**
     * Get all the userCashBacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserCashBackDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userCashBack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserCashBackDTO> findOne(Long id);

    /**
     * Delete the "id" userCashBack.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
