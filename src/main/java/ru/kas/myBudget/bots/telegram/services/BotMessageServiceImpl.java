package ru.kas.myBudget.bots.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static java.lang.Math.toIntExact;

@Service
public class BotMessageServiceImpl implements BotMessageService {

    private final TelegramBot telegramBot;

    @Autowired
    public BotMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void executeMessage(ExecuteMode executeMode, long chatId, Long messageId, String message,
                               InlineKeyboardMarkup inlineKeyboardMarkup) {
        switch (executeMode) {
            case SEND -> sendMessage(chatId, message, inlineKeyboardMarkup);
            case EDIT -> editMessage(chatId, messageId, message, inlineKeyboardMarkup);
            case DELETE -> deleteMessage(chatId, messageId);
        }
    }

    @Override
    public void executeSendMessage(long chatId, String message) {
        executeMessage(ExecuteMode.SEND, chatId, null, message, null);
    }

    @Override
    public void executeDeleteMessage(long chatId, long messageId) {
        executeMessage(ExecuteMode.DELETE, chatId, messageId, null, null);
    }

    private void sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        if (inlineKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        execute(telegramBot, sendMessage);
    }

    private void editMessage(long chatId, long messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(toIntExact(messageId));
        editMessage.enableHtml(true);
        editMessage.setText(message);
        if (inlineKeyboardMarkup != null) {
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        execute(telegramBot, editMessage);
    }

    private void deleteMessage(long chatId, long messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(toIntExact(messageId));

        execute(telegramBot, deleteMessage);
    }

    private void execute(TelegramBot telegramBot, BotApiMethod message) {
        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace(); // TODO Add logging to the project
        }
    }
}
