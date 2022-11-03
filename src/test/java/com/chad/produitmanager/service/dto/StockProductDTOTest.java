package com.chad.produitmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockProductDTO.class);
        StockProductDTO stockProductDTO1 = new StockProductDTO();
        stockProductDTO1.setId(1L);
        StockProductDTO stockProductDTO2 = new StockProductDTO();
        assertThat(stockProductDTO1).isNotEqualTo(stockProductDTO2);
        stockProductDTO2.setId(stockProductDTO1.getId());
        assertThat(stockProductDTO1).isEqualTo(stockProductDTO2);
        stockProductDTO2.setId(2L);
        assertThat(stockProductDTO1).isNotEqualTo(stockProductDTO2);
        stockProductDTO1.setId(null);
        assertThat(stockProductDTO1).isNotEqualTo(stockProductDTO2);
    }
}
