package com.chad.produitmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductSaleMapperTest {

    private ProductSaleMapper productSaleMapper;

    @BeforeEach
    public void setUp() {
        productSaleMapper = new ProductSaleMapperImpl();
    }
}
