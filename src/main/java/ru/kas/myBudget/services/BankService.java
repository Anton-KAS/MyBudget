package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
    List<Bank> findAll();

    Optional<Bank> findById(int currencyId);
}
