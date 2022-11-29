package komrachkov.anton.mybudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import komrachkov.anton.mybudget.models.Bank;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
}
