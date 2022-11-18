package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(int accountId);

    List<Account> findAllByTelegramUserId(long telegramUserId);

    void save(Account account);
}
