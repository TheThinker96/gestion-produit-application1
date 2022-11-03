package com.chad.produitmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.ProductTransaction;
import com.chad.produitmanager.domain.enumeration.TypeTransaction;
import com.chad.produitmanager.repository.ProductTransactionRepository;
import com.chad.produitmanager.service.dto.ProductTransactionDTO;
import com.chad.produitmanager.service.mapper.ProductTransactionMapper;
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
 * Integration tests for the {@link ProductTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductTransactionResourceIT {

    private static final TypeTransaction DEFAULT_TRANSACTION_TYPE = TypeTransaction.PRODUCTSALE;
    private static final TypeTransaction UPDATED_TRANSACTION_TYPE = TypeTransaction.SALECANCELLED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTransactionRepository productTransactionRepository;

    @Autowired
    private ProductTransactionMapper productTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTransactionMockMvc;

    private ProductTransaction productTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransaction createEntity(EntityManager em) {
        ProductTransaction productTransaction = new ProductTransaction()
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return productTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTransaction createUpdatedEntity(EntityManager em) {
        ProductTransaction productTransaction = new ProductTransaction()
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .description(UPDATED_DESCRIPTION);
        return productTransaction;
    }

    @BeforeEach
    public void initTest() {
        productTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTransaction() throws Exception {
        int databaseSizeBeforeCreate = productTransactionRepository.findAll().size();
        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);
        restProductTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProductTransactionWithExistingId() throws Exception {
        // Create the ProductTransaction with an existing ID
        productTransaction.setId(1L);
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        int databaseSizeBeforeCreate = productTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTransactionRepository.findAll().size();
        // set the field null
        productTransaction.setTransactionType(null);

        // Create the ProductTransaction, which fails.
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        restProductTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductTransactions() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get all the productTransactionList
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        // Get the productTransaction
        restProductTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, productTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProductTransaction() throws Exception {
        // Get the productTransaction
        restProductTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction
        ProductTransaction updatedProductTransaction = productTransactionRepository.findById(productTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedProductTransaction are not directly saved in db
        em.detach(updatedProductTransaction);
        updatedProductTransaction.transactionType(UPDATED_TRANSACTION_TYPE).description(UPDATED_DESCRIPTION);
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(updatedProductTransaction);

        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTransactionWithPatch() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction using partial update
        ProductTransaction partialUpdatedProductTransaction = new ProductTransaction();
        partialUpdatedProductTransaction.setId(productTransaction.getId());

        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProductTransactionWithPatch() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();

        // Update the productTransaction using partial update
        ProductTransaction partialUpdatedProductTransaction = new ProductTransaction();
        partialUpdatedProductTransaction.setId(productTransaction.getId());

        partialUpdatedProductTransaction.transactionType(UPDATED_TRANSACTION_TYPE).description(UPDATED_DESCRIPTION);

        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
        ProductTransaction testProductTransaction = productTransactionList.get(productTransactionList.size() - 1);
        assertThat(testProductTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testProductTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTransaction() throws Exception {
        int databaseSizeBeforeUpdate = productTransactionRepository.findAll().size();
        productTransaction.setId(count.incrementAndGet());

        // Create the ProductTransaction
        ProductTransactionDTO productTransactionDTO = productTransactionMapper.toDto(productTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTransaction in the database
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTransaction() throws Exception {
        // Initialize the database
        productTransactionRepository.saveAndFlush(productTransaction);

        int databaseSizeBeforeDelete = productTransactionRepository.findAll().size();

        // Delete the productTransaction
        restProductTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTransaction> productTransactionList = productTransactionRepository.findAll();
        assertThat(productTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
