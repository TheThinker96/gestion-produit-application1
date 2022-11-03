package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTransactionViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTransactionView.class);
        ProductTransactionView productTransactionView1 = new ProductTransactionView();
        productTransactionView1.setId(1L);
        ProductTransactionView productTransactionView2 = new ProductTransactionView();
        productTransactionView2.setId(productTransactionView1.getId());
        assertThat(productTransactionView1).isEqualTo(productTransactionView2);
        productTransactionView2.setId(2L);
        assertThat(productTransactionView1).isNotEqualTo(productTransactionView2);
        productTransactionView1.setId(null);
        assertThat(productTransactionView1).isNotEqualTo(productTransactionView2);
    }
}
