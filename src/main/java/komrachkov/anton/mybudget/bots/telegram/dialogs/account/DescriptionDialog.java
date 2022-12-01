package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

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

        addToDialogMap(chatId, AccountNames.DESCRIPTION, text, String.format(AccountNames.DESCRIPTION.getStepTextPattern(), "%s", text));
        return true;
    }
}
