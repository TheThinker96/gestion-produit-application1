package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.Product;
import com.chad.produitmanager.domain.ProductTransaction;
import com.chad.produitmanager.domain.StockProduct;
import com.chad.produitmanager.service.dto.ProductDTO;
import com.chad.produitmanager.service.dto.ProductTransactionDTO;
import com.chad.produitmanager.service.dto.StockProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTransaction} and its DTO {@link ProductTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductTransactionMapper extends EntityMapper<ProductTransactionDTO, ProductTransaction> {
    @Mapping(target = "stockProduct", source = "stockProduct", qualifiedByName = "stockProductId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ProductTransactionDTO toDto(ProductTransaction s);

    @Named("stockProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StockProductDTO toDtoStockProductId(StockProduct stockProduct);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
