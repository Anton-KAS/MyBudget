package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.AccountType;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
