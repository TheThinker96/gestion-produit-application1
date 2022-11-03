package com.chad.produitmanager.service.dto;

import com.chad.produitmanager.domain.enumeration.UserSaleAccountStatut;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.chad.produitmanager.domain.UserSaleAccount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserSaleAccountDTO implements Serializable {

    private Long id;

    private UserSaleAccountStatut statut;

    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSaleAccountStatut getStatut() {
        return statut;
    }

    public void setStatut(UserSaleAccountStatut statut) {
        this.statut = statut;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSaleAccountDTO)) {
            return false;
        }

        UserSaleAccountDTO userSaleAccountDTO = (UserSaleAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userSaleAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSaleAccountDTO{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
