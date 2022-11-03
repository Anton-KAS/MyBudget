package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

public class MenuText implements MessageText {
    public MenuText() {
    }

    @Override
    public String getText(TelegramUserService telegramUserService, long userId) {
        Optional<TelegramUser> telegramUser = telegramUserService.findById(userId);
        int accountNum = 0;
        if (telegramUser.isPresent()) {
            accountNum = telegramUser.get().getAccounts().size();
        }
        String accountText;
        switch (accountNum) {
            case 0:
                accountText = "нет счетов";
                break;
            case 1:
                accountText = accountNum + " счет";
                break;
            case 2:
            case 3:
            case 4:
                accountText = accountNum + "счета";
                break;
            default:
                accountText = accountNum + "счетов";

        }
        return String.format("Меню:\n" +
                "У вас %s", accountText);
    }
}
