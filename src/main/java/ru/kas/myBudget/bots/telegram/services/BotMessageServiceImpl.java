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
import ru.kas.myBudget.bots.telegram.dialogs.DialogPattern;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.io.Serializable;
import java.util.Optional;

import static java.lang.Math.toIntExact;

@Service
public class BotMessageServiceImpl implements BotMessageService {
    private final TelegramBot telegramBot;

    @Autowired
    public BotMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public Long executeMessage(ExecuteMode executeMode, long chatId, Long messageId, String message,
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
    public Long sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
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
    public Long editMessage(long chatId, long messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(toIntExact(messageId));
        editMessage.enableHtml(true);
        if (inlineKeyboardMarkup != null) {
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        editMessage.setText(message);
        return execute(telegramBot, editMessage);
    }

    @Override
    public Long deleteMessage(long chatId, long messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(toIntExact(messageId));

        return execute(telegramBot, deleteMessage);
    }

    public void executeAndUpdateUser(TelegramUserService telegramUserService,
                                     Update update, ExecuteMode executeMode, String text,
                                     InlineKeyboardMarkup inlineKeyboardMarkup) {
        removeInlineKeyboard(telegramUserService, update, executeMode);

        if (executeMode != null && text != null) {
            Long sendMessageId = executeMessage(executeMode, UpdateParameter.getChatId(update),
                    UpdateParameter.getMessageId(update), text, inlineKeyboardMarkup);
            if (inlineKeyboardMarkup != null && text.matches(DialogPattern.EDIT_NUM.getRegex())) {
                Optional<TelegramUser> telegramUser = telegramUserService.findById(UpdateParameter.getUserId(update));
                telegramUser.ifPresent(user -> telegramUserService.setLastMessage(user, sendMessageId, text));
            }
        }

        telegramUserService.checkUser(telegramUserService, update);
    }

    @Override
    public void updateUser(TelegramUserService telegramUserService, Update update) {
        executeAndUpdateUser(telegramUserService, update, null, null, null);
    }

    private void removeInlineKeyboard(TelegramUserService telegramUserService,
                                      Update update, ExecuteMode executeMode) {
        long userId = UpdateParameter.getUserId(update);

        if (executeMode == ExecuteMode.SEND) executeRemoveInlineKeyboard(update);

        if (executeMode == ExecuteMode.SEND && !update.hasCallbackQuery()) {
            Optional<TelegramUser> telegramUserOpt = telegramUserService.findById(userId);
            if (telegramUserOpt.isPresent()) {
                TelegramUser telegramUser = telegramUserOpt.get();
                Long messageIdToRemove = telegramUser.getLastMessageId();
                String messageTextToRemove = telegramUser.getLastMessageText();
                if (messageIdToRemove != null && messageTextToRemove != null) {
                    executeRemoveInlineKeyboard(userId, messageIdToRemove, messageTextToRemove);
                    telegramUser.removeLastMessage();
                    telegramUserService.save(telegramUser);
                }
            }
        }
    }

    private void executeRemoveInlineKeyboard(Update update) {
        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            String text = update.getCallbackQuery().getMessage().getText();
            executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(text), null);
        }
    }

    private void executeRemoveInlineKeyboard(long chatId, long messageId, String messageText) {
        executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(messageText), null);
    }

    private String cleanTextTags(String text) {
        return text.replaceAll("/(\\d+) ", "$1 ");
    }

    @Override
    public Long execute(TelegramBot telegramBot, BotApiMethod botApiMethod) {
        try {
            Serializable sendMessage = telegramBot.execute(botApiMethod);
            if (sendMessage != null) return getSendMessageId(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO: Add logging to the project
        }
        return null;
    }

    private Long getSendMessageId(Serializable sendMessage) {
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
            return Long.valueOf(sendMessageIdString);
        } catch (NumberFormatException ignore) {
            // TODO: Add project Logger
        }
        return null;
    }
}
