package ru.kas.myBudget.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 30, message = "Название должно быть длиной до 30 символов")
    @Column(name = "title")
    private String title;

    @Size(max = 100, message = "Описание должно быть длиной до 100 символов")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "tg_user_id", referencedColumnName = "id")
    private TelegramUser telegramUser;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;

    public Account() {
    }

    public Account(String title, String description, TelegramUser telegramUser, Currency currency, AccountType accountType, Bank bank) {
        this.title = title;
        this.description = description;
        this.telegramUser = telegramUser;
        this.currency = currency;
        this.accountType = accountType;
        this.bank = bank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Account{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
