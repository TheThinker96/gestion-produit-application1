package com.chad.produitmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCashBackDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCashBackDTO.class);
        UserCashBackDTO userCashBackDTO1 = new UserCashBackDTO();
        userCashBackDTO1.setId(1L);
        UserCashBackDTO userCashBackDTO2 = new UserCashBackDTO();
        assertThat(userCashBackDTO1).isNotEqualTo(userCashBackDTO2);
        userCashBackDTO2.setId(userCashBackDTO1.getId());
        assertThat(userCashBackDTO1).isEqualTo(userCashBackDTO2);
        userCashBackDTO2.setId(2L);
        assertThat(userCashBackDTO1).isNotEqualTo(userCashBackDTO2);
        userCashBackDTO1.setId(null);
        assertThat(userCashBackDTO1).isNotEqualTo(userCashBackDTO2);
    }
}
