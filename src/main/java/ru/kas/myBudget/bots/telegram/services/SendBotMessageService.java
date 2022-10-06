package ru.kas.myBudget.bots.telegram.services;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}
