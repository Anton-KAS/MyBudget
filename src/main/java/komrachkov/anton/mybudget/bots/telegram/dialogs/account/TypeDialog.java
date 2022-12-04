package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.TypeKeyboard;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.AccountType;
import komrachkov.anton.mybudget.services.AccountTypeService;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.CASH_ID;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class TypeDialog extends DialogImpl {
    private final static String ASK_TEXT = "Выберете тип счета:";
    private final TypeKeyboard typeKeyboard;
    private final AccountTypeService accountTypeService;

    @Autowired
    public TypeDialog(TelegramUserService telegramUserService, AccountDialogText messageText, TypeKeyboard keyboard,
                      AccountTypeService accountTypeService) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
        this.typeKeyboard = keyboard;
        this.accountTypeService = accountTypeService;
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null || callbackData.length <= OPERATION_DATA.ordinal()) return toDoList;

        int accountTypeId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
        AccountType accountType = accountTypeService.findById(accountTypeId).orElse(null);
        if (accountType == null) return toDoList;

        long chatId = UpdateParameter.getUserId(update);
        addToDialogMap(chatId, AccountNames.TYPE, String.valueOf(accountTypeId),
                String.format(AccountNames.TYPE.getStepTextPattern(), "%s", accountType.getTitleRu()));
        if (String.valueOf(accountTypeId).equals(CASH_ID.getId())) {
            addToDialogMap(chatId, AccountNames.BANK, null, null);
        }
        toDoList.setResultCommit(true);
        return toDoList;
    }
}
