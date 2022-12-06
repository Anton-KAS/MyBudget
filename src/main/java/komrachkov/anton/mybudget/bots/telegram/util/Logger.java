package komrachkov.anton.mybudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.4 (07.12.2022)
 */

public class Logger {
    static public String getLogStartText(Update update) {
        long userId = UpdateParameter.getUserId(update);
        long chatId = UpdateParameter.getChatId(update);
        return "user: " + userId + " | chat: " + chatId + " : ";
    }

    static public String getLogStartText(long userId, long chatId) {
        return "user: " + userId + " | chat: " + chatId + " : ";
    }

    static public String getLogStartText(long chatId) {
        return "user: --- | chat: " + chatId + " : ";
    }

    static public String getLogStartText(Update update, String dialogName) {
        long userId = UpdateParameter.getUserId(update);
        long chatId = UpdateParameter.getChatId(update);
        return "user: " + userId + " | chat: " + chatId + " : " + dialogName;
    }
}
