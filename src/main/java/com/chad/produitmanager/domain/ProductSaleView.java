package com.chad.produitmanager.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductSaleView.
 */
@Entity
@Table(name = "product_sale_view")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSaleView implements Serializable {

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

    @Column(name = "product_price", precision = 21, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

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

    public ProductSaleView id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public ProductSaleView productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStockName() {
        return this.stockName;
    }

    public ProductSaleView stockName(String stockName) {
        this.setStockName(stockName);
        return this;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProductSaleView quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
    }

    public ProductSaleView productPrice(BigDecimal productPrice) {
        this.setProductPrice(productPrice);
        return this;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public ProductSaleView total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProductSaleView createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProductSaleView createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductSaleView lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ProductSaleView lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof ProductSaleView)) {
            return false;
        }
        return id != null && id.equals(((ProductSaleView) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSaleView{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", stockName='" + getStockName() + "'" +
            ", quantite=" + getQuantite() +
            ", productPrice=" + getProductPrice() +
            ", total=" + getTotal() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
