package ru.kas.myBudget.bots.telegram.texts;

public class NoText  implements MessageText{
    @Override
    public MessageText setUserId(Long userId) {
        return this;
    }

    @Override
    public String getText() {
        return "Я пока не умею распознавать простой текст, но умею понимать команды из списка: /help";
    }
}
