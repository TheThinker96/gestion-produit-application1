package com.chad.produitmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prix", precision = 21, scale = 2)
    private BigDecimal prix;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productSales", "productTransactions", "product" }, allowSetters = true)
    private Set<StockProduct> stockProducts = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "stockProduct", "userSaleAccount" }, allowSetters = true)
    private Set<ProductSale> productSales = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "stockProduct", "product" }, allowSetters = true)
    private Set<ProductTransaction> productTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrix() {
        return this.prix;
    }

    public Product prix(BigDecimal prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Set<StockProduct> getStockProducts() {
        return this.stockProducts;
    }

    public void setStockProducts(Set<StockProduct> stockProducts) {
        if (this.stockProducts != null) {
            this.stockProducts.forEach(i -> i.setProduct(null));
        }
        if (stockProducts != null) {
            stockProducts.forEach(i -> i.setProduct(this));
        }
        this.stockProducts = stockProducts;
    }

    public Product stockProducts(Set<StockProduct> stockProducts) {
        this.setStockProducts(stockProducts);
        return this;
    }

    public Product addStockProduct(StockProduct stockProduct) {
        this.stockProducts.add(stockProduct);
        stockProduct.setProduct(this);
        return this;
    }

    public Product removeStockProduct(StockProduct stockProduct) {
        this.stockProducts.remove(stockProduct);
        stockProduct.setProduct(null);
        return this;
    }

    public Set<ProductSale> getProductSales() {
        return this.productSales;
    }

    public void setProductSales(Set<ProductSale> productSales) {
        if (this.productSales != null) {
            this.productSales.forEach(i -> i.setProduct(null));
        }
        if (productSales != null) {
            productSales.forEach(i -> i.setProduct(this));
        }
        this.productSales = productSales;
    }

    public Product productSales(Set<ProductSale> productSales) {
        this.setProductSales(productSales);
        return this;
    }

    public Product addProductSale(ProductSale productSale) {
        this.productSales.add(productSale);
        productSale.setProduct(this);
        return this;
    }

    public Product removeProductSale(ProductSale productSale) {
        this.productSales.remove(productSale);
        productSale.setProduct(null);
        return this;
    }

    public Set<ProductTransaction> getProductTransactions() {
        return this.productTransactions;
    }

    public void setProductTransactions(Set<ProductTransaction> productTransactions) {
        if (this.productTransactions != null) {
            this.productTransactions.forEach(i -> i.setProduct(null));
        }
        if (productTransactions != null) {
            productTransactions.forEach(i -> i.setProduct(this));
        }
        this.productTransactions = productTransactions;
    }

    public Product productTransactions(Set<ProductTransaction> productTransactions) {
        this.setProductTransactions(productTransactions);
        return this;
    }

    public Product addProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.add(productTransaction);
        productTransaction.setProduct(this);
        return this;
    }

    public Product removeProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.remove(productTransaction);
        productTransaction.setProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
