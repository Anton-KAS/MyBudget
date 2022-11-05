package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> findAll();
}
