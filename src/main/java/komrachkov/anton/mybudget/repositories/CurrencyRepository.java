package komrachkov.anton.mybudget.repositories;

import komrachkov.anton.mybudget.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByIsoCode(String isoCode);
}
