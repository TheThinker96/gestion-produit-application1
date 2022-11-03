package com.chad.produitmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StockProduct.
 */
@Entity
@Table(name = "stock_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantite")
    private Long quantite;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private Instant deliveryDate;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    @NotNull
    @Column(name = "prix_stock", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixStock;

    @OneToMany(mappedBy = "stockProduct")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "stockProduct", "userSaleAccount" }, allowSetters = true)
    private Set<ProductSale> productSales = new HashSet<>();

    @OneToMany(mappedBy = "stockProduct")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "stockProduct", "product" }, allowSetters = true)
    private Set<ProductTransaction> productTransactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "stockProducts", "productSales", "productTransactions" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StockProduct id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public StockProduct quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getName() {
        return this.name;
    }

    public StockProduct name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDeliveryDate() {
        return this.deliveryDate;
    }

    public StockProduct deliveryDate(Instant deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Instant getExpirationDate() {
        return this.expirationDate;
    }

    public StockProduct expirationDate(Instant expirationDate) {
        this.setExpirationDate(expirationDate);
        return this;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getPrixStock() {
        return this.prixStock;
    }

    public StockProduct prixStock(BigDecimal prixStock) {
        this.setPrixStock(prixStock);
        return this;
    }

    public void setPrixStock(BigDecimal prixStock) {
        this.prixStock = prixStock;
    }

    public Set<ProductSale> getProductSales() {
        return this.productSales;
    }

    public void setProductSales(Set<ProductSale> productSales) {
        if (this.productSales != null) {
            this.productSales.forEach(i -> i.setStockProduct(null));
        }
        if (productSales != null) {
            productSales.forEach(i -> i.setStockProduct(this));
        }
        this.productSales = productSales;
    }

    public StockProduct productSales(Set<ProductSale> productSales) {
        this.setProductSales(productSales);
        return this;
    }

    public StockProduct addProductSale(ProductSale productSale) {
        this.productSales.add(productSale);
        productSale.setStockProduct(this);
        return this;
    }

    public StockProduct removeProductSale(ProductSale productSale) {
        this.productSales.remove(productSale);
        productSale.setStockProduct(null);
        return this;
    }

    public Set<ProductTransaction> getProductTransactions() {
        return this.productTransactions;
    }

    public void setProductTransactions(Set<ProductTransaction> productTransactions) {
        if (this.productTransactions != null) {
            this.productTransactions.forEach(i -> i.setStockProduct(null));
        }
        if (productTransactions != null) {
            productTransactions.forEach(i -> i.setStockProduct(this));
        }
        this.productTransactions = productTransactions;
    }

    public StockProduct productTransactions(Set<ProductTransaction> productTransactions) {
        this.setProductTransactions(productTransactions);
        return this;
    }

    public StockProduct addProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.add(productTransaction);
        productTransaction.setStockProduct(this);
        return this;
    }

    public StockProduct removeProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.remove(productTransaction);
        productTransaction.setStockProduct(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockProduct product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockProduct)) {
            return false;
        }
        return id != null && id.equals(((StockProduct) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockProduct{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", name='" + getName() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", prixStock=" + getPrixStock() +
            "}";
    }
}
