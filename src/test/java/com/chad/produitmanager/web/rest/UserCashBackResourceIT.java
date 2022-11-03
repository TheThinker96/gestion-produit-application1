package com.chad.produitmanager.web.rest;

import static com.chad.produitmanager.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.UserCashBack;
import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import com.chad.produitmanager.repository.UserCashBackRepository;
import com.chad.produitmanager.service.dto.UserCashBackDTO;
import com.chad.produitmanager.service.mapper.UserCashBackMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link UserCashBackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserCashBackResourceIT {

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final UserSaleAccountStatut DEFAULT_STATUT = UserSaleAccountStatut.ACTIVE;
    private static final UserSaleAccountStatut UPDATED_STATUT = UserSaleAccountStatut.GONE;

    private static final String ENTITY_API_URL = "/api/user-cash-backs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserCashBackRepository userCashBackRepository;

    @Autowired
    private UserCashBackMapper userCashBackMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserCashBackMockMvc;

    private UserCashBack userCashBack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCashBack createEntity(EntityManager em) {
        UserCashBack userCashBack = new UserCashBack().montant(DEFAULT_MONTANT).balance(DEFAULT_BALANCE).statut(DEFAULT_STATUT);
        return userCashBack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCashBack createUpdatedEntity(EntityManager em) {
        UserCashBack userCashBack = new UserCashBack().montant(UPDATED_MONTANT).balance(UPDATED_BALANCE).statut(UPDATED_STATUT);
        return userCashBack;
    }

    @BeforeEach
    public void initTest() {
        userCashBack = createEntity(em);
    }

    @Test
    @Transactional
    void createUserCashBack() throws Exception {
        int databaseSizeBeforeCreate = userCashBackRepository.findAll().size();
        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);
        restUserCashBackMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeCreate + 1);
        UserCashBack testUserCashBack = userCashBackList.get(userCashBackList.size() - 1);
        assertThat(testUserCashBack.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testUserCashBack.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testUserCashBack.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    void createUserCashBackWithExistingId() throws Exception {
        // Create the UserCashBack with an existing ID
        userCashBack.setId(1L);
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        int databaseSizeBeforeCreate = userCashBackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCashBackMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCashBackRepository.findAll().size();
        // set the field null
        userCashBack.setMontant(null);

        // Create the UserCashBack, which fails.
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        restUserCashBackMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCashBackRepository.findAll().size();
        // set the field null
        userCashBack.setBalance(null);

        // Create the UserCashBack, which fails.
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        restUserCashBackMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCashBackRepository.findAll().size();
        // set the field null
        userCashBack.setStatut(null);

        // Create the UserCashBack, which fails.
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        restUserCashBackMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserCashBacks() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        // Get all the userCashBackList
        restUserCashBackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCashBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }

    @Test
    @Transactional
    void getUserCashBack() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        // Get the userCashBack
        restUserCashBackMockMvc
            .perform(get(ENTITY_API_URL_ID, userCashBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userCashBack.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(sameNumber(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserCashBack() throws Exception {
        // Get the userCashBack
        restUserCashBackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserCashBack() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();

        // Update the userCashBack
        UserCashBack updatedUserCashBack = userCashBackRepository.findById(userCashBack.getId()).get();
        // Disconnect from session so that the updates on updatedUserCashBack are not directly saved in db
        em.detach(updatedUserCashBack);
        updatedUserCashBack.montant(UPDATED_MONTANT).balance(UPDATED_BALANCE).statut(UPDATED_STATUT);
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(updatedUserCashBack);

        restUserCashBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCashBackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
        UserCashBack testUserCashBack = userCashBackList.get(userCashBackList.size() - 1);
        assertThat(testUserCashBack.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testUserCashBack.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserCashBack.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void putNonExistingUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCashBackDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserCashBackWithPatch() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();

        // Update the userCashBack using partial update
        UserCashBack partialUpdatedUserCashBack = new UserCashBack();
        partialUpdatedUserCashBack.setId(userCashBack.getId());

        partialUpdatedUserCashBack.statut(UPDATED_STATUT);

        restUserCashBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCashBack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCashBack))
            )
            .andExpect(status().isOk());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
        UserCashBack testUserCashBack = userCashBackList.get(userCashBackList.size() - 1);
        assertThat(testUserCashBack.getMontant()).isEqualByComparingTo(DEFAULT_MONTANT);
        assertThat(testUserCashBack.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testUserCashBack.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void fullUpdateUserCashBackWithPatch() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();

        // Update the userCashBack using partial update
        UserCashBack partialUpdatedUserCashBack = new UserCashBack();
        partialUpdatedUserCashBack.setId(userCashBack.getId());

        partialUpdatedUserCashBack.montant(UPDATED_MONTANT).balance(UPDATED_BALANCE).statut(UPDATED_STATUT);

        restUserCashBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCashBack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCashBack))
            )
            .andExpect(status().isOk());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
        UserCashBack testUserCashBack = userCashBackList.get(userCashBackList.size() - 1);
        assertThat(testUserCashBack.getMontant()).isEqualByComparingTo(UPDATED_MONTANT);
        assertThat(testUserCashBack.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testUserCashBack.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void patchNonExistingUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userCashBackDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserCashBack() throws Exception {
        int databaseSizeBeforeUpdate = userCashBackRepository.findAll().size();
        userCashBack.setId(count.incrementAndGet());

        // Create the UserCashBack
        UserCashBackDTO userCashBackDTO = userCashBackMapper.toDto(userCashBack);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCashBackMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCashBackDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCashBack in the database
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserCashBack() throws Exception {
        // Initialize the database
        userCashBackRepository.saveAndFlush(userCashBack);

        int databaseSizeBeforeDelete = userCashBackRepository.findAll().size();

        // Delete the userCashBack
        restUserCashBackMockMvc
            .perform(delete(ENTITY_API_URL_ID, userCashBack.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCashBack> userCashBackList = userCashBackRepository.findAll();
        assertThat(userCashBackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
