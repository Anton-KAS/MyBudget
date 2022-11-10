package ru.kas.myBudget.bots.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import java.io.Serializable;
import java.lang.reflect.Method;

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
    public Long executeSendMessage(long chatId, String message) {
        return executeMessage(ExecuteMode.SEND, chatId, null, message, null);
    }

    @Override
    public Long executeDeleteMessage(long chatId, long messageId) {
        return executeMessage(ExecuteMode.DELETE, chatId, messageId, null, null);
    }

    @Override
    public Long executeRemoveInlineKeyboard(Update update) {
        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            String text = update.getCallbackQuery().getMessage().getText();
            return executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(text), null);
        }
        return null;
    }

    @Override
    public Long executeRemoveInlineKeyboard(long chatId, long messageId, String messageText) {
        return executeMessage(ExecuteMode.EDIT, chatId, messageId, cleanTextTags(messageText), null);
    }

    private Long sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
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
    public Long execute(TelegramBot telegramBot, BotApiMethod botApiMethod) {
        try {
            Serializable sendMessage = telegramBot.execute(botApiMethod);
            return getSendMessageId(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO: Add logging to the project
        }
        return null;
    }

    private Long editMessage(long chatId, long messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(toIntExact(messageId));
        editMessage.enableHtml(true);
        editMessage.setText(message);
        if (inlineKeyboardMarkup != null) {
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        return execute(telegramBot, editMessage);
    }

    private Long deleteMessage(long chatId, long messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(toIntExact(messageId));

        return execute(telegramBot, deleteMessage);
    }

    private String cleanTextTags(String text) {
        return text.replaceAll("/(\\d+) ", "$1 ");
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
