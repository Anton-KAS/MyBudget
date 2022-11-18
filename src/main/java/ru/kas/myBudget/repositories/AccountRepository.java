package ru.kas.myBudget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kas.myBudget.models.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(int accountId);

    Optional<Account> findByTelegramUser(Long telegramUserId);

    List<Account> findAllByTelegramUser(Long telegramUserId);
}
