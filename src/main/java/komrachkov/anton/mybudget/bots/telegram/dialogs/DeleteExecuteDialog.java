package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.services.AccountService;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.*;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class DeleteExecuteDialog extends MainDialogImpl {

    private final CallbackContainer callbackContainer;
    private final AccountService accountService;
    private final static String COMMIT_MESSAGE = "удалил...";
    private final static String EXCEPTION_MESSAGE = "ничего не удалил...";

    @Autowired
    public DeleteExecuteDialog(TelegramUserService telegramUserService, CallbackContainer callbackContainer,
                               AccountService accountService) {
        super(telegramUserService);
        this.callbackContainer = callbackContainer;
        this.accountService = accountService;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        DialogsState.removeAllDialogs(chatId);

        ToDoList toDoList = new ToDoList();
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData != null && callbackData.length > OPERATION_DATA.ordinal()
                && callbackData[OPERATION.ordinal()].equals("delete")) { // TODO: rewrite magic word delete

            if (callbackData[FROM.ordinal()].equals(EDIT_ACCOUNT.getName())) {
                String returnTo = ACCOUNTS.getName();
                int idToDelete = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
                accountService.deleteById(idToDelete);

                toDoList.addToDo(ExecuteMode.POPUP, update, COMMIT_MESSAGE);
                toDoList.addAll(callbackContainer.retrieve(returnTo).execute(update));
                return toDoList;
            }
        }

        if (callbackData != null) {
            toDoList.addToDo(ExecuteMode.POPUP, update, EXCEPTION_MESSAGE);
        }
        toDoList.addAll(callbackContainer.retrieve(MENU.getName()).execute(update));
        System.out.println("Nothing to delete");

        return toDoList;
    }
}
