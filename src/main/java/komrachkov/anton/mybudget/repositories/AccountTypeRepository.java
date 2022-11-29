package komrachkov.anton.mybudget.repositories;

import komrachkov.anton.mybudget.models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
