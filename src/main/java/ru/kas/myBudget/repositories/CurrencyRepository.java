package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
