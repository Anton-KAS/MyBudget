package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.TypeKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.TYPE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;

public class TypeDialog extends DialogImpl {
    private final AccountTypeService accountTypeService;
    private final static String ASK_TEXT = "Выберете тип счета:";
    private final TypeKeyboard typeKeyboard = (TypeKeyboard) keyboard;

    public TypeDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      MessageText messageText, TypeKeyboard keyboard, AccountTypeService accountTypeService) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
        this.accountTypeService = accountTypeService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        typeKeyboard.setAccountTypeService(accountTypeService);
        setData(update);
        executeData(update, executeMode);
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getUserId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update);

        if (callbackData == null || callbackData.length <= CALLBACK_OPERATION_DATA_INDEX.getIndex()) return false;

        int accountTypeId = Integer.parseInt(callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);
        AccountType accountType = accountTypeService.findById(accountTypeId).orElse(null);
        if (accountType == null) return false;

        addToDialogMap(chatId, TYPE, String.valueOf(accountTypeId),
                String.format(TYPE.getStepTextPattern(), "%s", accountType.getTitleRu()));
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
