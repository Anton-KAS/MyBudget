package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.BanksKeyboard;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.BankService;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class BankDialog extends DialogImpl {
    private final static String ASK_TEXT = "Выберете банк счета:";
    private final BanksKeyboard banksKeyboard;
    private final BankService bankService;

    @Autowired
    public BankDialog(TelegramUserService telegramUserService, AccountDialogText messageText, BanksKeyboard keyboard,
                      BankService bankService) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
        this.banksKeyboard = keyboard;
        this.bankService = bankService;
    }

    @Override
    public ToDoList commit(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        ToDoList toDoList = new ToDoList();

        Integer bankId;
        if (callbackData != null && callbackData.length > OPERATION_DATA.ordinal())
            bankId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
        else return toDoList;

        Optional<Bank> bank = bankService.findById(bankId);
        if (bank.isEmpty()) return toDoList;

        String text = String.format(AccountNames.BANK.getStepTextPattern(),
                "%s", bank.get().getTitleRu() + " (" + bank.get().getCountry().getTitleRu() + ")");
        long chatId = UpdateParameter.getChatId(update);
        addToDialogMap(chatId, AccountNames.BANK, String.valueOf(bankId), text);

        toDoList.setResultCommit(true);
        return toDoList;
    }
}
