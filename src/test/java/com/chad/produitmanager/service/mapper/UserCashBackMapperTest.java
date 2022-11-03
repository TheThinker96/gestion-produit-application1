package com.chad.produitmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserCashBackMapperTest {

    private UserCashBackMapper userCashBackMapper;

    @BeforeEach
    public void setUp() {
        userCashBackMapper = new UserCashBackMapperImpl();
    }
}
