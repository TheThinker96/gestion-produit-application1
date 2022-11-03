package com.chad.produitmanager.domain;

import com.chad.produitmanager.domain.enumeration.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductTransaction.
 */
@Entity
@Table(name = "product_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TypeTransaction transactionType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSales", "productTransactions", "product" }, allowSetters = true)
    private StockProduct stockProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = { "stockProducts", "productSales", "productTransactions" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeTransaction getTransactionType() {
        return this.transactionType;
    }

    public ProductTransaction transactionType(TypeTransaction transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(TypeTransaction transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductTransaction description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StockProduct getStockProduct() {
        return this.stockProduct;
    }

    public void setStockProduct(StockProduct stockProduct) {
        this.stockProduct = stockProduct;
    }

    public ProductTransaction stockProduct(StockProduct stockProduct) {
        this.setStockProduct(stockProduct);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductTransaction product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTransaction)) {
            return false;
        }
        return id != null && id.equals(((ProductTransaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransaction{" +
            "id=" + getId() +
            ", transactionType='" + getTransactionType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
