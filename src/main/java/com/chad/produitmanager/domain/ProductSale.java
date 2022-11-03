package com.chad.produitmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductSale.
 */
@Entity
@Table(name = "product_sale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantite", nullable = false)
    private Long quantite;

    @NotNull
    @Column(name = "prix_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixTotal;

    @NotNull
    @Column(name = "statut", nullable = false)
    private Boolean statut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "stockProducts", "productSales", "productTransactions" }, allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSales", "productTransactions", "product" }, allowSetters = true)
    private StockProduct stockProduct;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productSales" }, allowSetters = true)
    private UserSaleAccount userSaleAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductSale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public ProductSale quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixTotal() {
        return this.prixTotal;
    }

    public ProductSale prixTotal(BigDecimal prixTotal) {
        this.setPrixTotal(prixTotal);
        return this;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Boolean getStatut() {
        return this.statut;
    }

    public ProductSale statut(Boolean statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSale product(Product product) {
        this.setProduct(product);
        return this;
    }

    public StockProduct getStockProduct() {
        return this.stockProduct;
    }

    public void setStockProduct(StockProduct stockProduct) {
        this.stockProduct = stockProduct;
    }

    public ProductSale stockProduct(StockProduct stockProduct) {
        this.setStockProduct(stockProduct);
        return this;
    }

    public UserSaleAccount getUserSaleAccount() {
        return this.userSaleAccount;
    }

    public void setUserSaleAccount(UserSaleAccount userSaleAccount) {
        this.userSaleAccount = userSaleAccount;
    }

    public ProductSale userSaleAccount(UserSaleAccount userSaleAccount) {
        this.setUserSaleAccount(userSaleAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSale)) {
            return false;
        }
        return id != null && id.equals(((ProductSale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSale{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", prixTotal=" + getPrixTotal() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
