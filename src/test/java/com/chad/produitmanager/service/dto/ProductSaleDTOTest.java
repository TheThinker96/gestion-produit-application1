package com.chad.produitmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSaleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSaleDTO.class);
        ProductSaleDTO productSaleDTO1 = new ProductSaleDTO();
        productSaleDTO1.setId(1L);
        ProductSaleDTO productSaleDTO2 = new ProductSaleDTO();
        assertThat(productSaleDTO1).isNotEqualTo(productSaleDTO2);
        productSaleDTO2.setId(productSaleDTO1.getId());
        assertThat(productSaleDTO1).isEqualTo(productSaleDTO2);
        productSaleDTO2.setId(2L);
        assertThat(productSaleDTO1).isNotEqualTo(productSaleDTO2);
        productSaleDTO1.setId(null);
        assertThat(productSaleDTO1).isNotEqualTo(productSaleDTO2);
    }
}
