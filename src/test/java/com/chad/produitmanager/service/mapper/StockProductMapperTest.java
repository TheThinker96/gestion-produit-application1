package com.chad.produitmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockProductMapperTest {

    private StockProductMapper stockProductMapper;

    @BeforeEach
    public void setUp() {
        stockProductMapper = new StockProductMapperImpl();
    }
}
