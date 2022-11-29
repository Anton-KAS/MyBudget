package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class UnknownDialog extends CommandControllerImpl implements Dialog {

    public UnknownDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    public boolean commit(Update update) {
        return false;
    }

    @Override
    public void skip(Update update) {

    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {

    }

    @Override
    public void setData(Update update) {

    }

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {

    }

    @Override
    public void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text) {

    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        return null;
    }
}
