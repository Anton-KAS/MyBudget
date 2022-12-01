package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.BanksKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.BankService;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

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
        return true;
    }
}
