package com.chad.produitmanager.web.rest;

import static com.chad.produitmanager.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.StockProduct;
import com.chad.produitmanager.repository.StockProductRepository;
import com.chad.produitmanager.service.dto.StockProductDTO;
import com.chad.produitmanager.service.mapper.StockProductMapper;
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
 * Integration tests for the {@link StockProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StockProductResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_PRIX_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_STOCK = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/stock-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockProductRepository stockProductRepository;

    @Autowired
    private StockProductMapper stockProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockProductMockMvc;

    private StockProduct stockProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockProduct createEntity(EntityManager em) {
        StockProduct stockProduct = new StockProduct()
            .quantite(DEFAULT_QUANTITE)
            .name(DEFAULT_NAME)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .prixStock(DEFAULT_PRIX_STOCK);
        return stockProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockProduct createUpdatedEntity(EntityManager em) {
        StockProduct stockProduct = new StockProduct()
            .quantite(UPDATED_QUANTITE)
            .name(UPDATED_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .prixStock(UPDATED_PRIX_STOCK);
        return stockProduct;
    }

    @BeforeEach
    public void initTest() {
        stockProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createStockProduct() throws Exception {
        int databaseSizeBeforeCreate = stockProductRepository.findAll().size();
        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);
        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeCreate + 1);
        StockProduct testStockProduct = stockProductList.get(stockProductList.size() - 1);
        assertThat(testStockProduct.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testStockProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockProduct.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testStockProduct.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testStockProduct.getPrixStock()).isEqualByComparingTo(DEFAULT_PRIX_STOCK);
    }

    @Test
    @Transactional
    void createStockProductWithExistingId() throws Exception {
        // Create the StockProduct with an existing ID
        stockProduct.setId(1L);
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        int databaseSizeBeforeCreate = stockProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockProductRepository.findAll().size();
        // set the field null
        stockProduct.setName(null);

        // Create the StockProduct, which fails.
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockProductRepository.findAll().size();
        // set the field null
        stockProduct.setDeliveryDate(null);

        // Create the StockProduct, which fails.
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpirationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockProductRepository.findAll().size();
        // set the field null
        stockProduct.setExpirationDate(null);

        // Create the StockProduct, which fails.
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockProductRepository.findAll().size();
        // set the field null
        stockProduct.setPrixStock(null);

        // Create the StockProduct, which fails.
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        restStockProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStockProducts() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        // Get all the stockProductList
        restStockProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].prixStock").value(hasItem(sameNumber(DEFAULT_PRIX_STOCK))));
    }

    @Test
    @Transactional
    void getStockProduct() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        // Get the stockProduct
        restStockProductMockMvc
            .perform(get(ENTITY_API_URL_ID, stockProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.prixStock").value(sameNumber(DEFAULT_PRIX_STOCK)));
    }

    @Test
    @Transactional
    void getNonExistingStockProduct() throws Exception {
        // Get the stockProduct
        restStockProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockProduct() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();

        // Update the stockProduct
        StockProduct updatedStockProduct = stockProductRepository.findById(stockProduct.getId()).get();
        // Disconnect from session so that the updates on updatedStockProduct are not directly saved in db
        em.detach(updatedStockProduct);
        updatedStockProduct
            .quantite(UPDATED_QUANTITE)
            .name(UPDATED_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .prixStock(UPDATED_PRIX_STOCK);
        StockProductDTO stockProductDTO = stockProductMapper.toDto(updatedStockProduct);

        restStockProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
        StockProduct testStockProduct = stockProductList.get(stockProductList.size() - 1);
        assertThat(testStockProduct.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testStockProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockProduct.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testStockProduct.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testStockProduct.getPrixStock()).isEqualByComparingTo(UPDATED_PRIX_STOCK);
    }

    @Test
    @Transactional
    void putNonExistingStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockProductWithPatch() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();

        // Update the stockProduct using partial update
        StockProduct partialUpdatedStockProduct = new StockProduct();
        partialUpdatedStockProduct.setId(stockProduct.getId());

        partialUpdatedStockProduct
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .prixStock(UPDATED_PRIX_STOCK);

        restStockProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockProduct))
            )
            .andExpect(status().isOk());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
        StockProduct testStockProduct = stockProductList.get(stockProductList.size() - 1);
        assertThat(testStockProduct.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testStockProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockProduct.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testStockProduct.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testStockProduct.getPrixStock()).isEqualByComparingTo(UPDATED_PRIX_STOCK);
    }

    @Test
    @Transactional
    void fullUpdateStockProductWithPatch() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();

        // Update the stockProduct using partial update
        StockProduct partialUpdatedStockProduct = new StockProduct();
        partialUpdatedStockProduct.setId(stockProduct.getId());

        partialUpdatedStockProduct
            .quantite(UPDATED_QUANTITE)
            .name(UPDATED_NAME)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .prixStock(UPDATED_PRIX_STOCK);

        restStockProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockProduct))
            )
            .andExpect(status().isOk());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
        StockProduct testStockProduct = stockProductList.get(stockProductList.size() - 1);
        assertThat(testStockProduct.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testStockProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockProduct.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testStockProduct.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testStockProduct.getPrixStock()).isEqualByComparingTo(UPDATED_PRIX_STOCK);
    }

    @Test
    @Transactional
    void patchNonExistingStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockProduct() throws Exception {
        int databaseSizeBeforeUpdate = stockProductRepository.findAll().size();
        stockProduct.setId(count.incrementAndGet());

        // Create the StockProduct
        StockProductDTO stockProductDTO = stockProductMapper.toDto(stockProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockProduct in the database
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockProduct() throws Exception {
        // Initialize the database
        stockProductRepository.saveAndFlush(stockProduct);

        int databaseSizeBeforeDelete = stockProductRepository.findAll().size();

        // Delete the stockProduct
        restStockProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockProduct> stockProductList = stockProductRepository.findAll();
        assertThat(stockProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
