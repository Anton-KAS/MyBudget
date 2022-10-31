package ru.kas.myBudget.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Название должно быть длиной до 100 символов")
    @Column(name = "title_en")
    private String titleEn;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Название должно быть длиной до 100 символов")
    @Column(name = "title_ru")
    private String titleRu;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "custom")
    private boolean custom;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "bank")
    private List<Account> accounts;

    public Bank() {
    }

    public Bank(String titleEn, String titleRu, boolean custom, Country country) {
        this.titleEn = titleEn;
        this.titleRu = titleRu;
        this.custom = custom;
        this.country = country;
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

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", titleEn='" + titleEn + '\'' +
                ", titleRu='" + titleRu + '\'' +
                ", custom=" + custom +
                '}';
    }
}
