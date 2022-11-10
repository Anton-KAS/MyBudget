package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.TypeKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CURRENT_DIALOG_STEP;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.TYPE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;

public class TypeDialog implements Dialog, Callback {

    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final AccountTypeService accountTypeService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String ASK_TEXT = "Выберете тип счета:";

    public TypeDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      AccountTypeService accountTypeService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.accountTypeService = accountTypeService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        int dialogStep = Integer.parseInt(dialogsMap.get(getUserId(update)).get(CURRENT_DIALOG_STEP.getDialogId()));
        long userId = getUserId(update);

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new TypeKeyboard(accountTypeService).getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        int accountTypeId = Integer.parseInt(getCallbackData(update)[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);

        Map<String, String> dialogSteps = dialogsMap.get(getChatId(update));
        dialogSteps.put(TYPE.getDialogId(), String.valueOf(accountTypeId));

        Optional<AccountType> accountType = accountTypeService.findById(accountTypeId);
        assert accountType.isPresent();

        dialogSteps.put(TYPE.getDialogIdText(),
                String.format(TYPE.getDialogTextPattern(), "%s", accountType.get().getTitleRu()));

        checkUserInDb(telegramUserService, update);
        return true;
    }
}
