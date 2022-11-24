package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Currency;

import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByIsoCode(String isoCode);
}
