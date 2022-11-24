package ru.kas.myBudget.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Entity
@Table(name = "account")
@Getter
@Setter
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

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

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

    public Account(String title, String description, BigDecimal startBalance, BigDecimal currentBalance,
                   Date createdAt, Date updatedAt, TelegramUser telegramUser, Currency currency,
                   AccountType accountType, Bank bank) {
        this.title = title;
        this.description = description;
        this.startBalance = startBalance;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.telegramUser = telegramUser;
        this.currency = currency;
        this.accountType = accountType;
        this.bank = bank;
    }

    public Account(String title, String description, BigDecimal startBalance, BigDecimal currentBalance,
                   TelegramUser telegramUser, Currency currency, AccountType accountType, Bank bank) {
        this.title = title;
        this.description = description;
        this.startBalance = startBalance;
        this.currentBalance = currentBalance;
        this.telegramUser = telegramUser;
        this.currency = currency;
        this.accountType = accountType;
        this.bank = bank;
    }

    public BigDecimal getStartBalanceWithScale() {
        BigDecimal numberToBasic = new BigDecimal(currency.getNumberToBasic());
        return startBalance.divide(numberToBasic, RoundingMode.HALF_UP)
                .setScale(String.valueOf(numberToBasic).length() - 1, RoundingMode.HALF_UP);
    }

    public void setStartBalanceWithScale(BigDecimal startBalance) {
        BigDecimal numberToBasic = new BigDecimal(currency.getNumberToBasic());
        this.startBalance = startBalance.multiply(numberToBasic).setScale(0, RoundingMode.HALF_UP);
    }

    public BigDecimal getCurrentBalanceWithScale() {
        BigDecimal numberToBasic = new BigDecimal(currency.getNumberToBasic());
        return currentBalance.divide(numberToBasic, RoundingMode.HALF_UP)
                .setScale(String.valueOf(numberToBasic).length() - 1, RoundingMode.HALF_UP);
    }

    public void setCurrentBalanceWithScale(BigDecimal currentBalance) {
        BigDecimal numberToBasic = new BigDecimal(currency.getNumberToBasic());
        this.currentBalance = currentBalance.multiply(numberToBasic).setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startBalance=" + startBalance +
                ", currentBalance=" + currentBalance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && title.equals(account.title) && Objects.equals(description, account.description)
                && startBalance.equals(account.startBalance) && currentBalance.equals(account.currentBalance)
                && telegramUser.equals(account.telegramUser) && currency.equals(account.currency)
                && accountType.equals(account.accountType) && Objects.equals(bank, account.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startBalance, currentBalance, telegramUser, currency, accountType, bank);
    }
}
