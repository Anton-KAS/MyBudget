package ru.kas.myBudget.bots.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;

import java.util.List;

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

    private SendMessage getSendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        return sendMessage;
    }
}
