package ru.kas.myBudget.bots.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.util.UpdateParameter.getUserId;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
public class BotMessageServiceImpl implements BotMessageService {
    private final TelegramBot telegramBot;

    @Autowired
    public BotMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public Integer executeMessage(ExecuteMode executeMode, long chatId, Integer messageId, String message,
                                  InlineKeyboardMarkup inlineKeyboardMarkup) {
        switch (executeMode) {
            case SEND -> {
                return sendMessage(chatId, message, inlineKeyboardMarkup);
            }
            case EDIT -> {
                return editMessage(chatId, messageId, message, inlineKeyboardMarkup);
            }
            case DELETE -> {
                return deleteMessage(chatId, messageId);
            }
        }
        return null;
    }

    @Override
    public Integer sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        if (inlineKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        return execute(telegramBot, sendMessage);
    }

    @Override
    public Integer editMessage(long chatId, int messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.enableHtml(true);
        if (inlineKeyboardMarkup != null) {
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        editMessage.setText(message);
        return execute(telegramBot, editMessage);
    }

    @Override
    public Integer deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        return execute(telegramBot, deleteMessage);
    }

    public void executeAndUpdateUser(TelegramUserService telegramUserService,
                                     Update update, ExecuteMode executeMode, String text,
                                     InlineKeyboardMarkup inlineKeyboardMarkup) {
        removeInlineKeyboard(telegramUserService, update, executeMode);

        if (executeMode == null) return;
        if (text == null) return;

        Integer sendMessageId = executeMessage(executeMode, UpdateParameter.getChatId(update),
                UpdateParameter.getMessageId(update), text, inlineKeyboardMarkup);

        boolean hasMatch = Arrays.stream(text.split("\n")).anyMatch(n -> n.matches(".*/(\\d+) .*"));
        if (inlineKeyboardMarkup == null && !hasMatch) return;

        Optional<TelegramUser> telegramUser = telegramUserService.findById(getUserId(update));
        telegramUser.ifPresent(user -> telegramUserService.setLastMessage(user, sendMessageId, text));
    }

    @Override
    public void updateUser(TelegramUserService telegramUserService, Update update) {
        removeInlineKeyboard(telegramUserService, update, null);
    }

    private void removeInlineKeyboard(TelegramUserService telegramUserService,
                                      Update update, ExecuteMode executeMode) {
        if (executeMode == ExecuteMode.SEND) executeRemoveInlineKeyboard(update);

        long userId = getUserId(update);
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        if (telegramUser == null) return;

        if (executeMode == ExecuteMode.SEND && !update.hasCallbackQuery()) {
            Integer messageIdToRemove = telegramUser.getLastMessageId();
            if (messageIdToRemove == null) return;

            String messageTextToRemove = getMessageTextToRemove(update, telegramUser, messageIdToRemove);
            if (messageTextToRemove == null) return;

            executeRemoveInlineKeyboard(userId, messageIdToRemove, messageTextToRemove);
            telegramUser.removeLastMessage();
        }

        telegramUserService.save(telegramUser);
    }

    private String getMessageTextToRemove(Update update, TelegramUser telegramUser, int messageIdToRemove) {
        if (messageIdToRemove == UpdateParameter.getMessageId(update)) {
            if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getText();

            return UpdateParameter.getMessageText(update);
        }
        return telegramUser.getLastMessageText();
    }

    private void executeRemoveInlineKeyboard(Update update) {
        if (!update.hasCallbackQuery()) return;

        long chatId = UpdateParameter.getChatId(update);
        int messageId = UpdateParameter.getMessageId(update);

        String text = update.getCallbackQuery().getMessage().getText();
        executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(text), null);
    }

    private void executeRemoveInlineKeyboard(long chatId, int messageId, String messageText) {
        executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(messageText), null);
    }

    private String cleanTextTags(String text) {
        return text.replaceAll("/(\\d+) ", "$1 ");
    }

    @Override
    public Integer execute(TelegramBot telegramBot, BotApiMethod botApiMethod) {
        try {
            Serializable sendMessage = telegramBot.execute(botApiMethod);
            if (sendMessage != null) return getSendMessageId(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO: Add logging to the project
        }
        return null;
    }

    private Integer getSendMessageId(Serializable sendMessage) {
        String sendMessageString = sendMessage.toString();

        String PARAMETER = "messageId=";
        int startIndex = sendMessageString.indexOf(PARAMETER);
        if (startIndex == -1) return null;

        startIndex = startIndex + PARAMETER.length();
        int endIndex = sendMessageString.substring(startIndex).indexOf(",");
        if (endIndex == -1) return null;

        endIndex = endIndex + startIndex;
        String sendMessageIdString = sendMessageString.substring(startIndex, endIndex);
        try {
            return Integer.valueOf(sendMessageIdString);
        } catch (NumberFormatException ignore) {
            // TODO: Add project Logger
        }
        return null;
    }
}
