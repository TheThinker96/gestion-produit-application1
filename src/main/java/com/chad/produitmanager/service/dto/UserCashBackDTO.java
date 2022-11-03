package com.chad.produitmanager.service.dto;

import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.chad.produitmanager.domain.UserCashBack} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserCashBackDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal montant;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private UserSaleAccountStatut statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserSaleAccountStatut getStatut() {
        return statut;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCashBackDTO)) {
            return false;
        }

        UserCashBackDTO userCashBackDTO = (UserCashBackDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userCashBackDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCashBackDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", balance=" + getBalance() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
