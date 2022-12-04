package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.models.AccountType;
import komrachkov.anton.mybudget.services.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.TYPE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class TypeKeyboard extends DialogKeyboardImpl {
    private final AccountTypeService accountTypeService;
    private final static String TEXT_BUTTON_PATTERN = "%s";

    @Autowired
    public TypeKeyboard(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        String callbackFormat = String.format(CALLBACK_FORMAT, currentDialogName, currentDialogName, TYPE.getName(), "%s");

        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (AccountType account : accountTypeService.findAll()) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, account.getTitleRu()),
                            String.format(callbackFormat, account.getId()));
        }
        return inlineKeyboardBuilder.build();
    }
}
