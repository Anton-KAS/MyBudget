package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.START_BALANCE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class StartBalanceKeyboard extends DialogKeyboardImpl {

    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();
        return inlineKeyboardBuilder
                .addRow().addNextButton(currentDialogName, START_BALANCE.getName())
                .build();
    }
}
