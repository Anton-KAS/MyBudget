package komrachkov.anton.mybudget.models;

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
@Table(name = "currency")
@Getter
@Setter
public class Currency implements Comparable<Currency> {
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

    public String displayToUser() {
        return symbol + " - " + currencyRu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return id == currency.id && numberToBasic == currency.numberToBasic && currencyEn.equals(currency.currencyEn)
                && currencyRu.equals(currency.currencyRu) && symbol.equals(currency.symbol)
                && isoCode.equals(currency.isoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyEn, currencyRu, symbol, isoCode, numberToBasic);
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

    @Override
    public int compareTo(Currency o) {
        return currencyRu.compareTo(o.currencyRu);
    }
}
