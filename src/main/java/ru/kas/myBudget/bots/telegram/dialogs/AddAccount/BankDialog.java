package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.BanksKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;

public class BankDialog extends DialogImpl {
    private final BankService bankService;
    private final static String ASK_TEXT = "Выберете банк счета:";
    private final BanksKeyboard banksKeyboard = (BanksKeyboard) keyboard;

    public BankDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap,
                      BankService bankService) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, ASK_TEXT);
        this.bankService = bankService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        banksKeyboard.setBankService(bankService);
        super.executeByOrder(update, executeMode);
    }


    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update);

        Integer bankId;
        if (callbackData != null && callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex())
            bankId = Integer.parseInt(callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);
        else return false;

        Optional<Bank> bank = bankService.findById(bankId);
        if (bank.isEmpty()) return false;

        String text = String.format(BANK.getDialogTextPattern(),
                "%s", bank.get().getTitleRu() + " (" + bank.get().getCountry().getTitleRu() + ")");
        addToDialogMap(userId, BANK, String.valueOf(bankId), text);
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
