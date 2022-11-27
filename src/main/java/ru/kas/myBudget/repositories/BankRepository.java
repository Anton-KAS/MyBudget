package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Bank;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
}
