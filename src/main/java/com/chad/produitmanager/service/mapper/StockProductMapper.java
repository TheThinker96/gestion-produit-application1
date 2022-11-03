package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.Product;
import com.chad.produitmanager.domain.StockProduct;
import com.chad.produitmanager.service.dto.ProductDTO;
import com.chad.produitmanager.service.dto.StockProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockProduct} and its DTO {@link StockProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockProductMapper extends EntityMapper<StockProductDTO, StockProduct> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    StockProductDTO toDto(StockProduct s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
