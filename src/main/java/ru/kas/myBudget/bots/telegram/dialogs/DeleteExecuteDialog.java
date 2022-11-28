package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.util.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.*;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DeleteExecuteDialog extends MainDialogImpl {

    private final CallbackContainer callbackContainer;
    private final AccountService accountService;
    private final static String COMMIT_MESSAGE = "удалил...";
    private final static String EXCEPTION_MESSAGE = "ничего не удалил...";

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
        if (callbackData != null && callbackData.length > OPERATION_DATA.ordinal()
                && callbackData[OPERATION.ordinal()].equals("delete")) { // TODO: rewrite magic word delete

            if (callbackData[FROM.ordinal()].equals(EDIT_ACCOUNT.getName())) {
                String returnTo = ACCOUNTS.getName();
                int idToDelete = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
                accountService.deleteById(idToDelete);
                botMessageService.sendPopup(UpdateParameter.getCallbackQueryId(update).orElse(null), COMMIT_MESSAGE);
                callbackContainer.retrieve(returnTo).execute(update);
                return;
            }
        }

        if (callbackData != null) {
            botMessageService.sendPopup(UpdateParameter.getCallbackQueryId(update).orElse(null), EXCEPTION_MESSAGE);
        }
        callbackContainer.retrieve(MENU.getName()).execute(update);
        System.out.println("Nothing to delete");
    }
}
