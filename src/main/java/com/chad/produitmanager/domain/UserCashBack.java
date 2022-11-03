package com.chad.produitmanager.domain;

import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserCashBack.
 */
@Entity
@Table(name = "user_cash_back")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserCashBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "montant", precision = 21, scale = 2, nullable = false)
    private BigDecimal montant;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private UserSaleAccountStatut statut;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserCashBack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public UserCashBack montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public UserCashBack balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserSaleAccountStatut getStatut() {
        return this.statut;
    }

    public UserCashBack statut(UserSaleAccountStatut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCashBack)) {
            return false;
        }
        return id != null && id.equals(((UserCashBack) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCashBack{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", balance=" + getBalance() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
