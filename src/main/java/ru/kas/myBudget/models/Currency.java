package ru.kas.myBudget.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 50, message = "Название должно быть длиной до 50 символов")
    @Column(name = "currency_en")
    private String currencyEn;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 50, message = "Название должно быть длиной до 50 символов")
    @Column(name = "currency_ru")
    private String currencyRu;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 5, message = "Название должно быть длиной до 5 символов")
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "iso_code")
    @Size(max = 5, message = "Название должно быть длиной до 5 символов")
    private String isoCode;

    @Column(name = "number_to_basic")
    private int numberToBasic;

    @OneToMany(mappedBy = "currency")
    private List<Country> countries;

    @OneToMany(mappedBy = "currency")
    private List<Account> accounts;

    public Currency() {
    }

    public Currency(String currencyEn, String currencyRu, String symbol, String isoCode, int numberToBasic) {
        this.currencyEn = currencyEn;
        this.currencyRu = currencyRu;
        this.symbol = symbol;
        this.isoCode = isoCode;
        this.numberToBasic = numberToBasic;
    }

    public String getCurrencyEn() {
        return currencyEn;
    }

    public void setCurrencyEn(String currencyEn) {
        this.currencyEn = currencyEn;
    }

    public String getCurrencyRu() {
        return currencyRu;
    }

    public void setCurrencyRu(String currencyRu) {
        this.currencyRu = currencyRu;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public int getNumberToBasic() {
        return numberToBasic;
    }

    public void setNumberToBasic(int numberToBasic) {
        this.numberToBasic = numberToBasic;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyEn='" + currencyEn + '\'' +
                ", currencyRu='" + currencyRu + '\'' +
                ", symbol='" + symbol + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", numberToBasic=" + numberToBasic +
                '}';
    }
}