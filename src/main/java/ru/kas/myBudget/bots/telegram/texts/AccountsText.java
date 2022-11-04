package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.List;
import java.util.Optional;

public class AccountsText implements MessageText {
    public AccountsText() {
    }

    @Override
    public String getText(TelegramUserService telegramUserService, long userId) {
        Optional<TelegramUser> telegramUser = telegramUserService.findById(userId);
        StringBuilder accountTextBuilder = new StringBuilder();
        if (telegramUser.isPresent()) {
            List<Account> accounts = telegramUser.get().getAccounts();

            if (accounts.isEmpty()) {
                accountTextBuilder.append("\n Нет счетов");
            } else {
                int n = 1;
                for (Account account : accounts) {
                    accountTextBuilder.append("\n")
                            .append(n).append(" - ").append(account.getTitle())
                            .append(" - ").append(account.getDescription());
                    n++;
                }
            }
        } else {
            accountTextBuilder.append("\n Пользователь не найден");
        }

        return String.format("Счета:\n" +
                "%s", accountTextBuilder);
    }
}
