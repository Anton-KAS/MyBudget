package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    List<Currency> findAll();

    Optional<Currency> findById(int currencyId);
}
