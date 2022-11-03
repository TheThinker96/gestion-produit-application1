package com.chad.produitmanager.web.rest;

import static com.chad.produitmanager.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.ProductSaleView;
import com.chad.produitmanager.repository.ProductSaleViewRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ProductSaleViewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductSaleViewResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final BigDecimal DEFAULT_PRODUCT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/product-sale-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductSaleViewRepository productSaleViewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductSaleViewMockMvc;

    private ProductSaleView productSaleView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSaleView createEntity(EntityManager em) {
        ProductSaleView productSaleView = new ProductSaleView()
            .productName(DEFAULT_PRODUCT_NAME)
            .stockName(DEFAULT_STOCK_NAME)
            .quantite(DEFAULT_QUANTITE)
            .productPrice(DEFAULT_PRODUCT_PRICE)
            .total(DEFAULT_TOTAL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productSaleView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSaleView createUpdatedEntity(EntityManager em) {
        ProductSaleView productSaleView = new ProductSaleView()
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productSaleView;
    }

    @BeforeEach
    public void initTest() {
        productSaleView = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSaleView() throws Exception {
        int databaseSizeBeforeCreate = productSaleViewRepository.findAll().size();
        // Create the ProductSaleView
        restProductSaleViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSaleView testProductSaleView = productSaleViewList.get(productSaleViewList.size() - 1);
        assertThat(testProductSaleView.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductSaleView.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testProductSaleView.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testProductSaleView.getProductPrice()).isEqualByComparingTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testProductSaleView.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testProductSaleView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductSaleView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductSaleView.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductSaleView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductSaleViewWithExistingId() throws Exception {
        // Create the ProductSaleView with an existing ID
        productSaleView.setId(1L);

        int databaseSizeBeforeCreate = productSaleViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSaleViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductSaleViews() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        // Get all the productSaleViewList
        restProductSaleViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSaleView.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].stockName").value(hasItem(DEFAULT_STOCK_NAME)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(sameNumber(DEFAULT_PRODUCT_PRICE))))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductSaleView() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        // Get the productSaleView
        restProductSaleViewMockMvc
            .perform(get(ENTITY_API_URL_ID, productSaleView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSaleView.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.stockName").value(DEFAULT_STOCK_NAME))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.productPrice").value(sameNumber(DEFAULT_PRODUCT_PRICE)))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductSaleView() throws Exception {
        // Get the productSaleView
        restProductSaleViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSaleView() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();

        // Update the productSaleView
        ProductSaleView updatedProductSaleView = productSaleViewRepository.findById(productSaleView.getId()).get();
        // Disconnect from session so that the updates on updatedProductSaleView are not directly saved in db
        em.detach(updatedProductSaleView);
        updatedProductSaleView
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductSaleViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductSaleView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductSaleView))
            )
            .andExpect(status().isOk());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
        ProductSaleView testProductSaleView = productSaleViewList.get(productSaleViewList.size() - 1);
        assertThat(testProductSaleView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductSaleView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testProductSaleView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSaleView.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testProductSaleView.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testProductSaleView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductSaleView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductSaleView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductSaleView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSaleView.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSaleViewWithPatch() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();

        // Update the productSaleView using partial update
        ProductSaleView partialUpdatedProductSaleView = new ProductSaleView();
        partialUpdatedProductSaleView.setId(productSaleView.getId());

        partialUpdatedProductSaleView
            .quantite(UPDATED_QUANTITE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductSaleViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSaleView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSaleView))
            )
            .andExpect(status().isOk());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
        ProductSaleView testProductSaleView = productSaleViewList.get(productSaleViewList.size() - 1);
        assertThat(testProductSaleView.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductSaleView.getStockName()).isEqualTo(DEFAULT_STOCK_NAME);
        assertThat(testProductSaleView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSaleView.getProductPrice()).isEqualByComparingTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testProductSaleView.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testProductSaleView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductSaleView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductSaleView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductSaleView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductSaleViewWithPatch() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();

        // Update the productSaleView using partial update
        ProductSaleView partialUpdatedProductSaleView = new ProductSaleView();
        partialUpdatedProductSaleView.setId(productSaleView.getId());

        partialUpdatedProductSaleView
            .productName(UPDATED_PRODUCT_NAME)
            .stockName(UPDATED_STOCK_NAME)
            .quantite(UPDATED_QUANTITE)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductSaleViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSaleView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSaleView))
            )
            .andExpect(status().isOk());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
        ProductSaleView testProductSaleView = productSaleViewList.get(productSaleViewList.size() - 1);
        assertThat(testProductSaleView.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductSaleView.getStockName()).isEqualTo(UPDATED_STOCK_NAME);
        assertThat(testProductSaleView.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSaleView.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testProductSaleView.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testProductSaleView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductSaleView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductSaleView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductSaleView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSaleView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSaleView() throws Exception {
        int databaseSizeBeforeUpdate = productSaleViewRepository.findAll().size();
        productSaleView.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleViewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSaleView))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSaleView in the database
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSaleView() throws Exception {
        // Initialize the database
        productSaleViewRepository.saveAndFlush(productSaleView);

        int databaseSizeBeforeDelete = productSaleViewRepository.findAll().size();

        // Delete the productSaleView
        restProductSaleViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSaleView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSaleView> productSaleViewList = productSaleViewRepository.findAll();
        assertThat(productSaleViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
