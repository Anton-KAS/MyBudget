package ru.kas.myBudget.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "account_type")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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
