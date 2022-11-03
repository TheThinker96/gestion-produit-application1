package com.chad.produitmanager.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.chad.produitmanager.domain.ProductSale} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSaleDTO implements Serializable {

    private Long id;

    @NotNull
    private Long quantite;

    @NotNull
    private BigDecimal prixTotal;

    @NotNull
    private Boolean statut;

    private ProductDTO product;

    private StockProductDTO stockProduct;

    private UserSaleAccountDTO userSaleAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public StockProductDTO getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProductDTO stockProduct) {
        this.stockProduct = stockProduct;
    }

    public UserSaleAccountDTO getUserSaleAccount() {
        return userSaleAccount;
    }

    public void setUserSaleAccount(UserSaleAccountDTO userSaleAccount) {
        this.userSaleAccount = userSaleAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSaleDTO)) {
            return false;
        }

        ProductSaleDTO productSaleDTO = (ProductSaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productSaleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSaleDTO{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", prixTotal=" + getPrixTotal() +
            ", statut='" + getStatut() + "'" +
            ", product=" + getProduct() +
            ", stockProduct=" + getStockProduct() +
            ", userSaleAccount=" + getUserSaleAccount() +
            "}";
    }
}
