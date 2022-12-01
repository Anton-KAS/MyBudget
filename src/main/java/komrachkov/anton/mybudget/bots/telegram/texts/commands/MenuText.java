package komrachkov.anton.mybudget.bots.telegram.texts.commands;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;

import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class MenuText implements MessageText {
    private final TelegramUserService telegramUserService;
    private Long userId;

    public MenuText(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @Override
    public MenuText setChatId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(userId);

        Optional<TelegramUser> telegramUser = telegramUserService.findById(userId);
        int accountCount = 0;
        if (telegramUser.isPresent()) {
            accountCount = telegramUser.get().getAccounts().size();
        }
        StringBuilder accountText = new StringBuilder();
        switch (accountCount) {
            case 0 -> accountText.append("нет счетов");
            case 1 -> accountText.append(accountCount).append(" счет");
            case 2, 3, 4 -> accountText.append(accountCount).append(" счета");
            default -> accountText.append(accountCount).append(" счетов");
        }
        return String.format("Меню:\n" +
                "У вас %s", accountText);
    }
}
