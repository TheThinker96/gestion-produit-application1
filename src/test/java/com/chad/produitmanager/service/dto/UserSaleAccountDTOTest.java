package com.chad.produitmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.chad.produitmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserSaleAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSaleAccountDTO.class);
        UserSaleAccountDTO userSaleAccountDTO1 = new UserSaleAccountDTO();
        userSaleAccountDTO1.setId(1L);
        UserSaleAccountDTO userSaleAccountDTO2 = new UserSaleAccountDTO();
        assertThat(userSaleAccountDTO1).isNotEqualTo(userSaleAccountDTO2);
        userSaleAccountDTO2.setId(userSaleAccountDTO1.getId());
        assertThat(userSaleAccountDTO1).isEqualTo(userSaleAccountDTO2);
        userSaleAccountDTO2.setId(2L);
        assertThat(userSaleAccountDTO1).isNotEqualTo(userSaleAccountDTO2);
        userSaleAccountDTO1.setId(null);
        assertThat(userSaleAccountDTO1).isNotEqualTo(userSaleAccountDTO2);
    }
}
