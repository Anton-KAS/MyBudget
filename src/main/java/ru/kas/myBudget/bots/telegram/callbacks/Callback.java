package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.UpdateExtraction;

public interface Callback extends UpdateExtraction {

    default String[] getCallbackData(Update update) {
        if (update.hasCallbackQuery()) return update.getCallbackQuery().getData().split("_");
        return null;
    }
}
