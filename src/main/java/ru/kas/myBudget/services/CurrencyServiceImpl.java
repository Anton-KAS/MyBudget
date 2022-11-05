package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.repositories.CurrencyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }
}
