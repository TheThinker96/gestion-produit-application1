package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockProductViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockProductView.class);
        StockProductView stockProductView1 = new StockProductView();
        stockProductView1.setId(1L);
        StockProductView stockProductView2 = new StockProductView();
        stockProductView2.setId(stockProductView1.getId());
        assertThat(stockProductView1).isEqualTo(stockProductView2);
        stockProductView2.setId(2L);
        assertThat(stockProductView1).isNotEqualTo(stockProductView2);
        stockProductView1.setId(null);
        assertThat(stockProductView1).isNotEqualTo(stockProductView2);
    }
}
