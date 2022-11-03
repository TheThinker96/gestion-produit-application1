package com.chad.produitmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCashBackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCashBack.class);
        UserCashBack userCashBack1 = new UserCashBack();
        userCashBack1.setId(1L);
        UserCashBack userCashBack2 = new UserCashBack();
        userCashBack2.setId(userCashBack1.getId());
        assertThat(userCashBack1).isEqualTo(userCashBack2);
        userCashBack2.setId(2L);
        assertThat(userCashBack1).isNotEqualTo(userCashBack2);
        userCashBack1.setId(null);
        assertThat(userCashBack1).isNotEqualTo(userCashBack2);
    }
}
