package com.chad.produitmanager.domain;

import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserSaleAccount.
 */
@Entity
@Table(name = "user_sale_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSaleAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private UserSaleAccountStatut statut;

    @Column(name = "balance")
    private Double balance;

    @OneToMany(mappedBy = "userSaleAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product", "stockProduct", "userSaleAccount" }, allowSetters = true)
    private Set<ProductSale> productSales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserSaleAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSaleAccountStatut getStatut() {
        return this.statut;
    }

    public UserSaleAccount statut(UserSaleAccountStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    public Double getBalance() {
        return this.balance;
    }

    public UserSaleAccount balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<ProductSale> getProductSales() {
        return this.productSales;
    }

    public void setProductSales(Set<ProductSale> productSales) {
        if (this.productSales != null) {
            this.productSales.forEach(i -> i.setUserSaleAccount(null));
        }
        if (productSales != null) {
            productSales.forEach(i -> i.setUserSaleAccount(this));
        }
        this.productSales = productSales;
    }

    public UserSaleAccount productSales(Set<ProductSale> productSales) {
        this.setProductSales(productSales);
        return this;
    }

    public UserSaleAccount addProductSale(ProductSale productSale) {
        this.productSales.add(productSale);
        productSale.setUserSaleAccount(this);
        return this;
    }

    public UserSaleAccount removeProductSale(ProductSale productSale) {
        this.productSales.remove(productSale);
        productSale.setUserSaleAccount(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSaleAccount)) {
            return false;
        }
        return id != null && id.equals(((UserSaleAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSaleAccount{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
