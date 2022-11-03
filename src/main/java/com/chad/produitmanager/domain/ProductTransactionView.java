package com.chad.produitmanager.domain;

import com.chad.produitmanager.domain.enumeration.TypeTransaction;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductTransactionView.
 */
@Entity
@Table(name = "product_transaction_view")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductTransactionView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "quantite")
    private Long quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TypeTransaction transactionType;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductTransactionView id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public ProductTransactionView productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStockName() {
        return this.stockName;
    }

    public ProductTransactionView stockName(String stockName) {
        this.setStockName(stockName);
        return this;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProductTransactionView quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public TypeTransaction getTransactionType() {
        return this.transactionType;
    }

    public ProductTransactionView transactionType(TypeTransaction transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(TypeTransaction transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductTransactionView description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProductTransactionView createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductTransactionView createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductTransactionView lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ProductTransactionView lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTransactionView)) {
            return false;
        }
        return id != null && id.equals(((ProductTransactionView) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransactionView{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", stockName='" + getStockName() + "'" +
            ", quantite=" + getQuantite() +
            ", transactionType='" + getTransactionType() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
