package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockProduct.class);
        StockProduct stockProduct1 = new StockProduct();
        stockProduct1.setId(1L);
        StockProduct stockProduct2 = new StockProduct();
        stockProduct2.setId(stockProduct1.getId());
        assertThat(stockProduct1).isEqualTo(stockProduct2);
        stockProduct2.setId(2L);
        assertThat(stockProduct1).isNotEqualTo(stockProduct2);
        stockProduct1.setId(null);
        assertThat(stockProduct1).isNotEqualTo(stockProduct2);
    }
}
