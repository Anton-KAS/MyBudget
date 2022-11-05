package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
}
