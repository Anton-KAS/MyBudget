package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class DescriptionDialog extends DialogImpl {
    public final static int MAX_DESCRIPTION_LENGTH = 100;
    private final static String ASK_TEXT = "Введите описание счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Описание должно быть до %s символов";

    public DescriptionDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                             MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getUserId(update);
        if (update.hasCallbackQuery()) return false;

        long chatId = UpdateParameter.getChatId(update);
        String text = UpdateParameter.getMessageText(update);

        if (text.length() > MAX_DESCRIPTION_LENGTH) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    String.format(VERIFY_EXCEPTION_TEXT, MAX_DESCRIPTION_LENGTH), null);
            return false;
        }

        addToDialogMap(chatId, DESCRIPTION, text, String.format(DESCRIPTION.getStepTextPattern(), "%s", text));
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
