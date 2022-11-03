package com.chad.produitmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSaleAccountMapperTest {

    private UserSaleAccountMapper userSaleAccountMapper;

    @BeforeEach
    public void setUp() {
        userSaleAccountMapper = new UserSaleAccountMapperImpl();
    }
}
