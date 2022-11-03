package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.UserCashBack;
import com.chad.produitmanager.service.dto.UserCashBackDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserCashBack} and its DTO {@link UserCashBackDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserCashBackMapper extends EntityMapper<UserCashBackDTO, UserCashBack> {}
