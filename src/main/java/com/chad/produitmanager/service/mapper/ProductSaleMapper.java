package com.chad.produitmanager.service.mapper;

import com.chad.produitmanager.domain.Product;
import com.chad.produitmanager.domain.ProductSale;
import com.chad.produitmanager.domain.StockProduct;
import com.chad.produitmanager.domain.UserSaleAccount;
import com.chad.produitmanager.service.dto.ProductDTO;
import com.chad.produitmanager.service.dto.ProductSaleDTO;
import com.chad.produitmanager.service.dto.StockProductDTO;
import com.chad.produitmanager.service.dto.UserSaleAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSale} and its DTO {@link ProductSaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductSaleMapper extends EntityMapper<ProductSaleDTO, ProductSale> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "stockProduct", source = "stockProduct", qualifiedByName = "stockProductId")
    @Mapping(target = "userSaleAccount", source = "userSaleAccount", qualifiedByName = "userSaleAccountId")
    ProductSaleDTO toDto(ProductSale s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("stockProductId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StockProductDTO toDtoStockProductId(StockProduct stockProduct);

    @Named("userSaleAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserSaleAccountDTO toDtoUserSaleAccountId(UserSaleAccount userSaleAccount);
}
