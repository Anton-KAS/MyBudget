package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.List;
import java.util.Optional;

public class AccountsText implements MessageText {
    private final TelegramUserService telegramUserService;
    private final Long userId;

    public AccountsText(TelegramUserService telegramUserService, Long userId) {
        this.telegramUserService = telegramUserService;
        this.userId = userId;
    }

    @Override
    public String getText() {
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
