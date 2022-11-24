package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Account;

import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findById(int accountId);

}
