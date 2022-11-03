package com.chad.produitmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.StockProductView;
import com.chad.produitmanager.repository.StockProductViewRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link StockProductViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StockProductViewResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final String DEFAULT_STOCK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/stock-product-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockProductViewRepository stockProductViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockProductViewMockMvc;

    private StockProductView stockProductView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockProductView createEntity(EntityManager em) {
        StockProductView stockProductView = new StockProductView()
            .quantite(DEFAULT_QUANTITE)
            .stockName(DEFAULT_STOCK_NAME)
            .productName(DEFAULT_PRODUCT_NAME)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return stockProductView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockProductView createUpdatedEntity(EntityManager em) {
        StockProductView stockProductView = new StockProductView()
            .quantite(UPDATED_QUANTITE)
            .stockName(UPDATED_STOCK_NAME)
            .productName(UPDATED_PRODUCT_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return stockProductView;
    }

    @BeforeEach
    public void initTest() {
        stockProductView = createEntity(em);
    }

    @Test
    @Transactional
    void createStockProductView() throws Exception {
        int databaseSizeBeforeCreate = stockProductViewRepository.findAll().size();
        // Create the StockProductView
        restStockProductViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isCreated());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeCreate + 1);
        StockProductView testStockProductView = stockProductViewList.get(stockProductViewList.size() - 1);
        assertThat(testStockProductView.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testStockProductView.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testStockProductView.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testStockProductView.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testStockProductView.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testStockProductView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStockProductView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStockProductView.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testStockProductView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createStockProductViewWithExistingId() throws Exception {
        // Create the StockProductView with an existing ID
        stockProductView.setId(1L);

        int databaseSizeBeforeCreate = stockProductViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockProductViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStockProductViews() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        // Get all the stockProductViewList
        restStockProductViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockProductView.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].stockName").value(hasItem(DEFAULT_STOCK_NAME)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getStockProductView() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        // Get the stockProductView
        restStockProductViewMockMvc
            .perform(get(ENTITY_API_URL_ID, stockProductView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockProductView.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.stockName").value(DEFAULT_STOCK_NAME))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStockProductView() throws Exception {
        // Get the stockProductView
        restStockProductViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockProductView() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();

        // Update the stockProductView
        StockProductView updatedStockProductView = stockProductViewRepository.findById(stockProductView.getId()).get();
        // Disconnect from session so that the updates on updatedStockProductView are not directly saved in db
        em.detach(updatedStockProductView);
        updatedStockProductView
            .quantite(UPDATED_QUANTITE)
            .stockName(UPDATED_STOCK_NAME)
            .productName(UPDATED_PRODUCT_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStockProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStockProductView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStockProductView))
            )
            .andExpect(status().isOk());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
        StockProductView testStockProductView = stockProductViewList.get(stockProductViewList.size() - 1);
        assertThat(testStockProductView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testStockProductView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testStockProductView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testStockProductView.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testStockProductView.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testStockProductView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStockProductView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStockProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStockProductView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockProductView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockProductViewWithPatch() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();

        // Update the stockProductView using partial update
        StockProductView partialUpdatedStockProductView = new StockProductView();
        partialUpdatedStockProductView.setId(stockProductView.getId());

        partialUpdatedStockProductView
            .quantite(UPDATED_QUANTITE)
            .productName(UPDATED_PRODUCT_NAME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restStockProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockProductView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockProductView))
            )
            .andExpect(status().isOk());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
        StockProductView testStockProductView = stockProductViewList.get(stockProductViewList.size() - 1);
        assertThat(testStockProductView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testStockProductView.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testStockProductView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testStockProductView.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testStockProductView.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testStockProductView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStockProductView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStockProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStockProductView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateStockProductViewWithPatch() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();

        // Update the stockProductView using partial update
        StockProductView partialUpdatedStockProductView = new StockProductView();
        partialUpdatedStockProductView.setId(stockProductView.getId());

        partialUpdatedStockProductView
            .quantite(UPDATED_QUANTITE)
            .stockName(UPDATED_STOCK_NAME)
            .productName(UPDATED_PRODUCT_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStockProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockProductView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockProductView))
            )
            .andExpect(status().isOk());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
        StockProductView testStockProductView = stockProductViewList.get(stockProductViewList.size() - 1);
        assertThat(testStockProductView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testStockProductView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testStockProductView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testStockProductView.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testStockProductView.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testStockProductView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStockProductView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStockProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStockProductView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockProductView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockProductView() throws Exception {
        int databaseSizeBeforeUpdate = stockProductViewRepository.findAll().size();
        stockProductView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockProductView in the database
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockProductView() throws Exception {
        // Initialize the database
        stockProductViewRepository.saveAndFlush(stockProductView);

        int databaseSizeBeforeDelete = stockProductViewRepository.findAll().size();

        // Delete the stockProductView
        restStockProductViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockProductView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockProductView> stockProductViewList = stockProductViewRepository.findAll();
        assertThat(stockProductViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
