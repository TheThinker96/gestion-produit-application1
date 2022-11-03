package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSale.class);
        ProductSale productSale1 = new ProductSale();
        productSale1.setId(1L);
        ProductSale productSale2 = new ProductSale();
        productSale2.setId(productSale1.getId());
        assertThat(productSale1).isEqualTo(productSale2);
        productSale2.setId(2L);
        assertThat(productSale1).isNotEqualTo(productSale2);
        productSale1.setId(null);
        assertThat(productSale1).isNotEqualTo(productSale2);
    }
}
