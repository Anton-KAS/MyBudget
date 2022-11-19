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

public class TitleDialog extends DialogImpl {
    public final static int MIN_TITLE_LENGTH = 2;
    public final static int MAX_TITLE_LENGTH = 30;
    private final static String ASK_TEXT = "Введите название счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Название должно быть от %s и до %s символов";

    public TitleDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getUserId(update);
        if (update.hasCallbackQuery()) return false;

        String text = UpdateParameter.getMessageText(update);

        if (text.length() < MIN_TITLE_LENGTH || text.length() > MAX_TITLE_LENGTH) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    String.format(VERIFY_EXCEPTION_TEXT, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH), null);
            return false;
        }

        addToDialogMap(chatId, TITLE, text, String.format(TITLE.getStepTextPattern(), "%s", text));
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
