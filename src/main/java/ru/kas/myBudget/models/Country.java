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
@Table(name = "country")
@Getter
@Setter
public class Country {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Название должно быть длиной до 50 символов")
    @Column(name = "title_en")
    private String titleEn;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 50, message = "Название должно быть длиной до 50 символов")
    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "custom")
    private boolean custom;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @OneToMany(mappedBy = "country")
    private List<Bank> banks;

    public Country() {
    }

    public Country(String titleEn, String titleRu, boolean custom, Currency currency) {
        this.titleEn = titleEn;
        this.titleRu = titleRu;
        this.custom = custom;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id && custom == country.custom && titleEn.equals(country.titleEn) && titleRu.equals(country.titleRu) && currency.equals(country.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titleEn, titleRu, custom, currency);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", titleEn='" + titleEn + '\'' +
                ", titleRu='" + titleRu + '\'' +
                ", custom=" + custom +
                '}';
    }
}
