package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.BankService;

import java.util.List;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.BANK;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class BanksKeyboard extends DialogKeyboardImpl {
    private final BankService bankService;
    private final static String TEXT_BUTTON_PATTERN = "%s (%s)";

    @Autowired
    public BanksKeyboard(BankService bankService) {
        super();
        this.bankService = bankService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        String callbackFormat = String.format(CALLBACK_FORMAT, currentDialogName, currentDialogName, BANK.getName(), "%s");

        List<Bank> banks = bankService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Bank bank : banks) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, bank.getTitleRu(), bank.getCountry().getTitleRu()),
                            String.format(callbackFormat, bank.getId()));
        }
        inlineKeyboardBuilder.addRow().addNextButton(ADD_ACCOUNT.getName(), BANK.getName());
        return inlineKeyboardBuilder.build();
    }

    public BanksKeyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
