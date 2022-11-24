package ru.kas.myBudget.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Entity
@Table(name = "bank")
@Getter
@Setter
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

    public String displayToUser() {
        return titleRu + " (" + country.getTitleRu() + ")";
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
