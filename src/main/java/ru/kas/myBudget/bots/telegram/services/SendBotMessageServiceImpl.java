package ru.kas.myBudget.bots.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;

import static java.lang.Math.toIntExact;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final TelegramBot telegramBot;

    @Autowired
    public SendBotMessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = getSendMessage(chatId, message);

        execute(telegramBot, sendMessage);
    }

    @Override
    public void sendMessageWithInlineKeyboard(long chatId, String message, InlineKeyboardMarkup markupInline) {
        SendMessage sendMessage = getSendMessage(chatId, message);
        sendMessage.setReplyMarkup(markupInline);

        execute(telegramBot, sendMessage);
    }

    @Override
    public void editMessage(long chatId, long messageId, String message) {
        EditMessageText editMessage = getEditMessage(chatId, messageId, message);

        execute(telegramBot, editMessage);
    }

    @Override
    public void editMessageWithInlineKeyboard(long chatId, long messageId, String message, InlineKeyboardMarkup markupInline) {
        EditMessageText editMessage = getEditMessage(chatId, messageId, message);
        editMessage.setReplyMarkup(markupInline);

        execute(telegramBot, editMessage);
    }

    @Override
    public void deleteMessage(long chatId, long messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(toIntExact(messageId));

        execute(telegramBot, deleteMessage);
    }

    private SendMessage getSendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        return sendMessage;
    }

    private EditMessageText getEditMessage(long chatId, long messageId, String message) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(toIntExact(messageId));
        editMessage.enableHtml(true);
        editMessage.setText(message);

        return editMessage;
    }
}
