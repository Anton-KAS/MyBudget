package ru.kas.myBudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

public interface UpdateExtraction {
    void execute(Update update);

    void execute(Update update, ExecuteMode executeMode);

    default long getUserId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        } else {
            return update.getMessage().getFrom().getId();
        }
    }

    default long getChatId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else {
            return update.getMessage().getChatId();
        }
    }

    default long getMessageId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getMessageId();
        } else {
            return update.getMessage().getMessageId();
        }
    }

    default void sendAndUpdateUser(TelegramUserService telegramUserService, BotMessageService botMessageService,
                         Update update, ExecuteMode executeMode, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        removeInlineKeyboard(telegramUserService, botMessageService, update, executeMode);

        Long sendMessageId = botMessageService.executeMessage(executeMode, getChatId(update), getMessageId(update),
                text, inlineKeyboardMarkup);
        Optional<TelegramUser> telegramUser = telegramUserService.findById(getUserId(update));
        telegramUser.ifPresent(user ->
                telegramUserService.setLastMessage(user, sendMessageId, text));

        checkUserInDb(telegramUserService, update);
    }

    default void checkUserInDb(TelegramUserService telegramUserService, Update update) {
        telegramUserService.findById(getUserId(update)).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser(update);
                    telegramUserService.save(telegramUser);
                }
        );
    }

    default void removeInlineKeyboard(TelegramUserService telegramUserService, BotMessageService botMessageService,
                                      Update update, ExecuteMode executeMode) {
        long userId = getUserId(update);

        if (executeMode == ExecuteMode.SEND) botMessageService.executeRemoveInlineKeyboard(update);

        if (executeMode == ExecuteMode.SEND && !update.hasCallbackQuery()) {
            Optional<TelegramUser> telegramUserOpt = telegramUserService.findById(userId);
            if (telegramUserOpt.isPresent()) {
                TelegramUser telegramUser = telegramUserOpt.get();
                Long messageIdToRemove = telegramUser.getLastMessageId();
                String messageTextToRemove = telegramUser.getLastMessageText();
                if (messageIdToRemove != null && messageTextToRemove != null) {
                    botMessageService.executeRemoveInlineKeyboard(userId, messageIdToRemove, messageTextToRemove);
                    telegramUser.removeLastMessage();
                    telegramUserService.save(telegramUser);
                }
            }
        }
    }
}
