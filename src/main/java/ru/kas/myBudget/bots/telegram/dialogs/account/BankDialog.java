package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.AccountDialog.BanksKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;

public class BankDialog extends DialogImpl {
    private final BankService bankService;
    private final static String ASK_TEXT = "Выберете банк счета:";
    private final BanksKeyboard banksKeyboard = (BanksKeyboard) keyboard;

    public BankDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      MessageText messageText, Keyboard keyboard, BankService bankService) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
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
        this.chatId = UpdateParameter.getChatId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        Integer bankId;
        if (callbackData != null && callbackData.length > OPERATION_DATA.ordinal())
            bankId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
        else return false;

        Optional<Bank> bank = bankService.findById(bankId);
        if (bank.isEmpty()) return false;

        String text = String.format(AccountNames.BANK.getStepTextPattern(),
                "%s", bank.get().getTitleRu() + " (" + bank.get().getCountry().getTitleRu() + ")");
        addToDialogMap(chatId, AccountNames.BANK, String.valueOf(bankId), text);
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
