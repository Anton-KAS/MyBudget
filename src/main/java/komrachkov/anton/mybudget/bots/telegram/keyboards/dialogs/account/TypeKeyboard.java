package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.models.AccountType;
import komrachkov.anton.mybudget.services.AccountTypeService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.TYPE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class TypeKeyboard extends DialogKeyboardImpl {
    private AccountTypeService accountTypeService;
    private final static String TEXT_BUTTON_PATTERN = "%s";

    public TypeKeyboard(String currentDialogName) {
        super(currentDialogName);
        this.callbackPattern = String.format(callbackPattern, TYPE.getName(), "%s");
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (AccountType account : accountTypeService.findAll()) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, account.getTitleRu()),
                            String.format(callbackPattern, account.getId()));
        }
        return inlineKeyboardBuilder.build();
    }

    public TypeKeyboard setAccountTypeService(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
        return this;
    }
}
