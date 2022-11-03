package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductSaleViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSaleView.class);
        ProductSaleView productSaleView1 = new ProductSaleView();
        productSaleView1.setId(1L);
        ProductSaleView productSaleView2 = new ProductSaleView();
        productSaleView2.setId(productSaleView1.getId());
        assertThat(productSaleView1).isEqualTo(productSaleView2);
        productSaleView2.setId(2L);
        assertThat(productSaleView1).isNotEqualTo(productSaleView2);
        productSaleView1.setId(null);
        assertThat(productSaleView1).isNotEqualTo(productSaleView2);
    }
}
