package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.Product;
import com.chad.produitmanager.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}
