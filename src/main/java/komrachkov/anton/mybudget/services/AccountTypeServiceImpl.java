package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import komrachkov.anton.mybudget.repositories.AccountTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
@Transactional(readOnly = true)
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public List<AccountType> findAll() {
        return accountTypeRepository.findAll();
    }

    @Override
    public Optional<AccountType> findById(int currencyId) {
        return accountTypeRepository.findById(currencyId);
    }
}
