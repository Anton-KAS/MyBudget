package komrachkov.anton.mybudget.repositories;

import komrachkov.anton.mybudget.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByIsoCode(String isoCode);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByCurrencyEnContains(String currencyEn);


    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByCurrencyRuContains(String currencyRu);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllBySymbolContains(String symbol);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByIsoCodeContains(String isoCode);
}
