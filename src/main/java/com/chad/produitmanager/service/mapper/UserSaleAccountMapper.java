package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.UserSaleAccount;
import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSaleAccount} and its DTO {@link UserSaleAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserSaleAccountMapper extends EntityMapper<UserSaleAccountDTO, UserSaleAccount> {}
