package com.chad.produitmanager.service.dto;

import com.chad.produitmanager.domain.enumeration.TypeTransaction;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.chad.produitmanager.domain.ProductTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private TypeTransaction transactionType;

    private String description;

    private StockProductDTO stockProduct;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeTransaction getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TypeTransaction transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StockProductDTO getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProductDTO stockProduct) {
        this.stockProduct = stockProduct;
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
        if (!(o instanceof ProductTransactionDTO)) {
            return false;
        }

        ProductTransactionDTO productTransactionDTO = (ProductTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransactionDTO{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", description='" + getDescription() + "'" +
            ", stockProduct=" + getStockProduct() +
            ", product=" + getProduct() +
            "}";
    }
}
