package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.repositories.BankRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
@Transactional(readOnly = true)
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> findById(int currencyId) {
        return bankRepository.findById(currencyId);
    }
}
