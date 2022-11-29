package komrachkov.anton.mybudget.repositories;

import komrachkov.anton.mybudget.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findById(int accountId);

}
