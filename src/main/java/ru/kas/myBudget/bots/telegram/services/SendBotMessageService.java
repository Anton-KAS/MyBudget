package ru.kas.myBudget.bots.telegram.services;

public interface SendBotMessageService {
    void sendMessage(long chatId, String message);
}
