package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.ConfirmDialog;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddConfirmDialog extends ConfirmDialog {

    public AddConfirmDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, messageText, keyboard);
    }
}
