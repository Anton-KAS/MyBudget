package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;

public class TitleDialog extends DialogImpl {
    private final static int MIN_TITLE_LENGTH = 2;
    private final static int MAX_TITLE_LENGTH = 30;
    private final static String ASK_TEXT = "Введите название счета:";
    private final static String VERIFY_EXCEPTION_TEXT = "Название должно быть от %s и до %s символов";

    public TitleDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, ASK_TEXT);
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        String text = UpdateParameter.getMessageText(update);

        if (text.length() < MIN_TITLE_LENGTH || text.length() > MAX_TITLE_LENGTH) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    String.format(VERIFY_EXCEPTION_TEXT, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH), null);
            return false;
        }

        addToDialogMap(userId, TITLE, text, String.format(TITLE.getDialogTextPattern(), "%s", text));
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
