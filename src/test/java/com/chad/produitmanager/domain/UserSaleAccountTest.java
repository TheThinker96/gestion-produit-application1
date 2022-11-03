package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserSaleAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSaleAccount.class);
        UserSaleAccount userSaleAccount1 = new UserSaleAccount();
        userSaleAccount1.setId(1L);
        UserSaleAccount userSaleAccount2 = new UserSaleAccount();
        userSaleAccount2.setId(userSaleAccount1.getId());
        assertThat(userSaleAccount1).isEqualTo(userSaleAccount2);
        userSaleAccount2.setId(2L);
        assertThat(userSaleAccount1).isNotEqualTo(userSaleAccount2);
        userSaleAccount1.setId(null);
        assertThat(userSaleAccount1).isNotEqualTo(userSaleAccount2);
    }
}
