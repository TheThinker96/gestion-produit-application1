package com.chad.produitmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.UserSaleAccount;
import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import com.chad.produitmanager.repository.UserSaleAccountRepository;
import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import com.chad.produitmanager.service.mapper.UserSaleAccountMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserSaleAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserSaleAccountResourceIT {

    private static final UserSaleAccountStatut DEFAULT_STATUT = UserSaleAccountStatut.ACTIVE;
    private static final UserSaleAccountStatut UPDATED_STATUT = UserSaleAccountStatut.GONE;

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String ENTITY_API_URL = "/api/user-sale-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserSaleAccountRepository userSaleAccountRepository;

    @Autowired
    private UserSaleAccountMapper userSaleAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSaleAccountMockMvc;

    private UserSaleAccount userSaleAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSaleAccount createEntity(EntityManager em) {
        UserSaleAccount userSaleAccount = new UserSaleAccount().statut(DEFAULT_STATUT).balance(DEFAULT_BALANCE);
        return userSaleAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSaleAccount createUpdatedEntity(EntityManager em) {
        UserSaleAccount userSaleAccount = new UserSaleAccount().statut(UPDATED_STATUT).balance(UPDATED_BALANCE);
        return userSaleAccount;
    }

    @BeforeEach
    public void initTest() {
        userSaleAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createUserSaleAccount() throws Exception {
        int databaseSizeBeforeCreate = userSaleAccountRepository.findAll().size();
        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);
        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testUserSaleAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createUserSaleAccountWithExistingId() throws Exception {
        // Create the UserSaleAccount with an existing ID
        userSaleAccount.setId(1L);
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        int databaseSizeBeforeCreate = userSaleAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSaleAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserSaleAccounts() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        // Get all the userSaleAccountList
        restUserSaleAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSaleAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        // Get the userSaleAccount
        restUserSaleAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, userSaleAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSaleAccount.getId().intValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserSaleAccount() throws Exception {
        // Get the userSaleAccount
        restUserSaleAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount
        UserSaleAccount updatedUserSaleAccount = userSaleAccountRepository.findById(userSaleAccount.getId()).get();
        // Disconnect from session so that the updates on updatedUserSaleAccount are not directly saved in db
        em.detach(updatedUserSaleAccount);
        updatedUserSaleAccount.statut(UPDATED_STATUT).balance(UPDATED_BALANCE);
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(updatedUserSaleAccount);

        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testUserSaleAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserSaleAccountWithPatch() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount using partial update
        UserSaleAccount partialUpdatedUserSaleAccount = new UserSaleAccount();
        partialUpdatedUserSaleAccount.setId(userSaleAccount.getId());

        partialUpdatedUserSaleAccount.statut(UPDATED_STATUT).balance(UPDATED_BALANCE);

        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSaleAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSaleAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testUserSaleAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateUserSaleAccountWithPatch() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();

        // Update the userSaleAccount using partial update
        UserSaleAccount partialUpdatedUserSaleAccount = new UserSaleAccount();
        partialUpdatedUserSaleAccount.setId(userSaleAccount.getId());

        partialUpdatedUserSaleAccount.statut(UPDATED_STATUT).balance(UPDATED_BALANCE);

        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSaleAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSaleAccount))
            )
            .andExpect(status().isOk());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
        UserSaleAccount testUserSaleAccount = userSaleAccountList.get(userSaleAccountList.size() - 1);
        assertThat(testUserSaleAccount.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testUserSaleAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userSaleAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserSaleAccount() throws Exception {
        int databaseSizeBeforeUpdate = userSaleAccountRepository.findAll().size();
        userSaleAccount.setId(count.incrementAndGet());

        // Create the UserSaleAccount
        UserSaleAccountDTO userSaleAccountDTO = userSaleAccountMapper.toDto(userSaleAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSaleAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSaleAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSaleAccount in the database
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserSaleAccount() throws Exception {
        // Initialize the database
        userSaleAccountRepository.saveAndFlush(userSaleAccount);

        int databaseSizeBeforeDelete = userSaleAccountRepository.findAll().size();

        // Delete the userSaleAccount
        restUserSaleAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, userSaleAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSaleAccount> userSaleAccountList = userSaleAccountRepository.findAll();
        assertThat(userSaleAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
