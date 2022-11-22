package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.*;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

public class DeleteExecuteDialog extends MainDialogImpl{

    private final CallbackContainer callbackContainer;
    private final AccountService accountService;

    public DeleteExecuteDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer, AccountService accountService) {
        super(botMessageService, telegramUserService);
        this.callbackContainer = callbackContainer;
        this.accountService = accountService;
    }

    @Override
    public void execute(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        DialogsMap.remove(chatId);
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData != null && callbackData.length > OPERATION_DATA.getIndex()
                && callbackData[OPERATION.getIndex()].equals("delete")) {
            if (callbackData[FROM.getIndex()].equals(EDIT_ACCOUNT.getName())) {
                String returnTo = ACCOUNTS.getName();
                int idToDelete = Integer.parseInt(callbackData[OPERATION_DATA.getIndex()]);
                accountService.deleteById(idToDelete);
                callbackContainer.retrieve(returnTo).execute(update);
                return;
            }
        }
        callbackContainer.retrieve(MENU.getName()).execute(update);
        System.out.println("Nothing to delete");
    }
}
