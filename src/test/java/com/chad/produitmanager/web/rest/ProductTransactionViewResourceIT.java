package com.chad.produitmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.ProductTransactionView;
import com.chad.produitmanager.domain.enumeration.TypeTransaction;
import com.chad.produitmanager.repository.ProductTransactionViewRepository;
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
 * Integration tests for the {@link ProductTransactionViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductTransactionViewResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final TypeTransaction DEFAULT_TRANSACTION_TYPE = TypeTransaction.PRODUCTSALE;
    private static final TypeTransaction UPDATED_TRANSACTION_TYPE = TypeTransaction.SALECANCELLED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/product-transaction-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTransactionViewRepository productTransactionViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTransactionViewMockMvc;

    private ProductTransactionView productTransactionView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransactionView createEntity(EntityManager em) {
        ProductTransactionView productTransactionView = new ProductTransactionView()
            .productName(DEFAULT_PRODUCT_NAME)
            .stockName(DEFAULT_STOCK_NAME)
            .quantite(DEFAULT_QUANTITE)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productTransactionView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransactionView createUpdatedEntity(EntityManager em) {
        ProductTransactionView productTransactionView = new ProductTransactionView()
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productTransactionView;
    }

    @BeforeEach
    public void initTest() {
        productTransactionView = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTransactionView() throws Exception {
        int databaseSizeBeforeCreate = productTransactionViewRepository.findAll().size();
        // Create the ProductTransactionView
        restProductTransactionViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTransactionView testProductTransactionView = productTransactionViewList.get(productTransactionViewList.size() - 1);
        assertThat(testProductTransactionView.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductTransactionView.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testProductTransactionView.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testProductTransactionView.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testProductTransactionView.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductTransactionView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTransactionView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTransactionView.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTransactionView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductTransactionViewWithExistingId() throws Exception {
        // Create the ProductTransactionView with an existing ID
        productTransactionView.setId(1L);

        int databaseSizeBeforeCreate = productTransactionViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTransactionViewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTransactionViews() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        // Get all the productTransactionViewList
        restProductTransactionViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTransactionView.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].stockName").value(hasItem(DEFAULT_STOCK_NAME)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductTransactionView() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        // Get the productTransactionView
        restProductTransactionViewMockMvc
            .perform(get(ENTITY_API_URL_ID, productTransactionView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTransactionView.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.stockName").value(DEFAULT_STOCK_NAME))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductTransactionView() throws Exception {
        // Get the productTransactionView
        restProductTransactionViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductTransactionView() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();

        // Update the productTransactionView
        ProductTransactionView updatedProductTransactionView = productTransactionViewRepository
            .findById(productTransactionView.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductTransactionView are not directly saved in db
        em.detach(updatedProductTransactionView);
        updatedProductTransactionView
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTransactionViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductTransactionView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductTransactionView))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
        ProductTransactionView testProductTransactionView = productTransactionViewList.get(productTransactionViewList.size() - 1);
        assertThat(testProductTransactionView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductTransactionView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testProductTransactionView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductTransactionView.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransactionView.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductTransactionView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTransactionView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTransactionView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTransactionView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTransactionView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTransactionViewWithPatch() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();

        // Update the productTransactionView using partial update
        ProductTransactionView partialUpdatedProductTransactionView = new ProductTransactionView();
        partialUpdatedProductTransactionView.setId(productTransactionView.getId());

        partialUpdatedProductTransactionView
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductTransactionViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransactionView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransactionView))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
        ProductTransactionView testProductTransactionView = productTransactionViewList.get(productTransactionViewList.size() - 1);
        assertThat(testProductTransactionView.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductTransactionView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testProductTransactionView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductTransactionView.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransactionView.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductTransactionView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTransactionView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTransactionView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTransactionView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductTransactionViewWithPatch() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();

        // Update the productTransactionView using partial update
        ProductTransactionView partialUpdatedProductTransactionView = new ProductTransactionView();
        partialUpdatedProductTransactionView.setId(productTransactionView.getId());

        partialUpdatedProductTransactionView
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTransactionViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransactionView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransactionView))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
        ProductTransactionView testProductTransactionView = productTransactionViewList.get(productTransactionViewList.size() - 1);
        assertThat(testProductTransactionView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductTransactionView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testProductTransactionView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductTransactionView.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransactionView.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductTransactionView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTransactionView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTransactionView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTransactionView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTransactionView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTransactionView() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionViewRepository.findAll().size();
        productTransactionView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransactionView in the database
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTransactionView() throws Exception {
        // Initialize the database
        productTransactionViewRepository.saveAndFlush(productTransactionView);

        int databaseSizeBeforeDelete = productTransactionViewRepository.findAll().size();

        // Delete the productTransactionView
        restProductTransactionViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTransactionView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTransactionView> productTransactionViewList = productTransactionViewRepository.findAll();
        assertThat(productTransactionViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
