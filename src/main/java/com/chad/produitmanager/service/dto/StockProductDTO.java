package com.chad.produitmanager.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.chad.produitmanager.domain.StockProduct} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockProductDTO implements Serializable {

    private Long id;

    private Long quantite;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    private Instant deliveryDate;

    @NotNull
    private Instant expirationDate;

    @NotNull
    private BigDecimal prixStock;

    private ProductDTO product;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getPrixStock() {
        return prixStock;
    }

    public void setPrixStock(BigDecimal prixStock) {
        this.prixStock = prixStock;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockProductDTO)) {
            return false;
        }

        StockProductDTO stockProductDTO = (StockProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stockProductDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockProductDTO{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", name='" + getName() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", prixStock=" + getPrixStock() +
            ", product=" + getProduct() +
            "}";
    }
}
