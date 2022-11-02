package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);

    List<Account> findAllByTelegramUserId(long telegramUserId);
}
