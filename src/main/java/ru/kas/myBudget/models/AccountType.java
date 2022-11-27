package ru.kas.myBudget.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Entity
@Table(name = "account_type")
@Getter
@Setter
public class AccountType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(max = 100, message = "Название должно быть длиной до 100 символов")
    @Column(name = "title_en")
    private String titleEn;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Название должно быть длиной до 100 символов")
    @Column(name = "title_ru")
    private String titleRu;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;

    public AccountType() {
    }

    public AccountType(String titleEn, String titleRu) {
        this.titleEn = titleEn;
        this.titleRu = titleRu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountType that = (AccountType) o;
        return id == that.id && titleEn.equals(that.titleEn) && titleRu.equals(that.titleRu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titleEn, titleRu);
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "id=" + id +
                ", titleEn='" + titleEn + '\'' +
                ", titleRu='" + titleRu + '\'' +
                '}';
    }
}
