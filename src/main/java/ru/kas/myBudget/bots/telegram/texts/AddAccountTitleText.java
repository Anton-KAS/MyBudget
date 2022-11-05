package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.services.TelegramUserService;

public class AddAccountTitleText implements MessageText {
    public AddAccountTitleText() {
    }

    @Override
    public String getText() {
        return """
                <b>Добавление счета</b>

                <b>1. Введите название счета:</b>
                <i>(для отмены введите /back)</i>""";
    }
}
