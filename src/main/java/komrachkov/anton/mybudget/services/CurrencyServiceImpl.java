package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import komrachkov.anton.mybudget.repositories.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

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

    @Override
    public Optional<Currency> findById(int currencyId) {
        return currencyRepository.findById(currencyId);
    }

    @Override
    public Optional<Currency> findByIsoCode(String isoCode) {
        return currencyRepository.findByIsoCode(isoCode);
    }

    @Override
    public List<Currency> getReserveCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        String[] isoCodes = {"USD", "EUR", "GBP"};
        for (String isoCode : isoCodes) {
            Optional<Currency> currency = findByIsoCode(isoCode);
            currency.ifPresent(currencies::add);
        }
        return currencies;
    }
}
