package com.chad.produitmanager.web.rest;

import static com.chad.produitmanager.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chad.produitmanager.IntegrationTest;
import com.chad.produitmanager.domain.ProductSale;
import com.chad.produitmanager.repository.ProductSaleRepository;
import com.chad.produitmanager.service.dto.ProductSaleDTO;
import com.chad.produitmanager.service.mapper.ProductSaleMapper;
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
 * Integration tests for the {@link ProductSaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductSaleResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;

    private static final BigDecimal DEFAULT_PRIX_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_TOTAL = new BigDecimal(2);

    private static final Boolean DEFAULT_STATUT = false;
    private static final Boolean UPDATED_STATUT = true;

    private static final String ENTITY_API_URL = "/api/product-sales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductSaleRepository productSaleRepository;

    @Autowired
    private ProductSaleMapper productSaleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductSaleMockMvc;

    private ProductSale productSale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSale createEntity(EntityManager em) {
        ProductSale productSale = new ProductSale().quantite(DEFAULT_QUANTITE).prixTotal(DEFAULT_PRIX_TOTAL).statut(DEFAULT_STATUT);
        return productSale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSale createUpdatedEntity(EntityManager em) {
        ProductSale productSale = new ProductSale().quantite(UPDATED_QUANTITE).prixTotal(UPDATED_PRIX_TOTAL).statut(UPDATED_STATUT);
        return productSale;
    }

    @BeforeEach
    public void initTest() {
        productSale = createEntity(em);
    }

    @Test
    @Transactional
    void createProductSale() throws Exception {
        int databaseSizeBeforeCreate = productSaleRepository.findAll().size();
        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);
        restProductSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSale testProductSale = productSaleList.get(productSaleList.size() - 1);
        assertThat(testProductSale.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testProductSale.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testProductSale.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    void createProductSaleWithExistingId() throws Exception {
        // Create the ProductSale with an existing ID
        productSale.setId(1L);
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        int databaseSizeBeforeCreate = productSaleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSaleRepository.findAll().size();
        // set the field null
        productSale.setQuantite(null);

        // Create the ProductSale, which fails.
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        restProductSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSaleRepository.findAll().size();
        // set the field null
        productSale.setPrixTotal(null);

        // Create the ProductSale, which fails.
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        restProductSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSaleRepository.findAll().size();
        // set the field null
        productSale.setStatut(null);

        // Create the ProductSale, which fails.
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        restProductSaleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductSales() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        // Get all the productSaleList
        restProductSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSale.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].prixTotal").value(hasItem(sameNumber(DEFAULT_PRIX_TOTAL))))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.booleanValue())));
    }

    @Test
    @Transactional
    void getProductSale() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        // Get the productSale
        restProductSaleMockMvc
            .perform(get(ENTITY_API_URL_ID, productSale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSale.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.prixTotal").value(sameNumber(DEFAULT_PRIX_TOTAL)))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductSale() throws Exception {
        // Get the productSale
        restProductSaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductSale() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();

        // Update the productSale
        ProductSale updatedProductSale = productSaleRepository.findById(productSale.getId()).get();
        // Disconnect from session so that the updates on updatedProductSale are not directly saved in db
        em.detach(updatedProductSale);
        updatedProductSale.quantite(UPDATED_QUANTITE).prixTotal(UPDATED_PRIX_TOTAL).statut(UPDATED_STATUT);
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(updatedProductSale);

        restProductSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSaleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
        ProductSale testProductSale = productSaleList.get(productSaleList.size() - 1);
        assertThat(testProductSale.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSale.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testProductSale.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void putNonExistingProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productSaleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productSaleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductSaleWithPatch() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();

        // Update the productSale using partial update
        ProductSale partialUpdatedProductSale = new ProductSale();
        partialUpdatedProductSale.setId(productSale.getId());

        partialUpdatedProductSale.quantite(UPDATED_QUANTITE).statut(UPDATED_STATUT);

        restProductSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSale))
            )
            .andExpect(status().isOk());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
        ProductSale testProductSale = productSaleList.get(productSaleList.size() - 1);
        assertThat(testProductSale.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSale.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testProductSale.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void fullUpdateProductSaleWithPatch() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();

        // Update the productSale using partial update
        ProductSale partialUpdatedProductSale = new ProductSale();
        partialUpdatedProductSale.setId(productSale.getId());

        partialUpdatedProductSale.quantite(UPDATED_QUANTITE).prixTotal(UPDATED_PRIX_TOTAL).statut(UPDATED_STATUT);

        restProductSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductSale))
            )
            .andExpect(status().isOk());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
        ProductSale testProductSale = productSaleList.get(productSaleList.size() - 1);
        assertThat(testProductSale.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testProductSale.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testProductSale.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void patchNonExistingProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productSaleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductSale() throws Exception {
        int databaseSizeBeforeUpdate = productSaleRepository.findAll().size();
        productSale.setId(count.incrementAndGet());

        // Create the ProductSale
        ProductSaleDTO productSaleDTO = productSaleMapper.toDto(productSale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductSaleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productSaleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductSale in the database
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductSale() throws Exception {
        // Initialize the database
        productSaleRepository.saveAndFlush(productSale);

        int databaseSizeBeforeDelete = productSaleRepository.findAll().size();

        // Delete the productSale
        restProductSaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, productSale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSale> productSaleList = productSaleRepository.findAll();
        assertThat(productSaleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
