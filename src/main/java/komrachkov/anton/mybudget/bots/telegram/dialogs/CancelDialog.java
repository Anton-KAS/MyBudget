package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.CancelKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.CancelText;
import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.TO;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.START_FROM_CALLBACK;

/**
 * Отмена и сброс диалога и ожидания ввода от пользователя, возврат к стартовой точке меню
 *
 * @author Anton Komrachkov
 * @version 1.0
 */

@Component
public class CancelDialog extends CommandControllerImpl {
    private final CallbackContainer callbackContainer;

    @Autowired
    public CancelDialog(TelegramUserService telegramUserService, CancelText messageText, CancelKeyboard keyboard,
                        CallbackContainer callbackContainer) {
        super(telegramUserService, messageText, keyboard);
        this.callbackContainer = callbackContainer;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode(Update update) {
        this.defaultExecuteMode = ExecuteMode.getCallbackExecuteMode();
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ToDoList toDoList = new ToDoList();
        long chatId = UpdateParameter.getChatId(update);

        Optional<String> callbackQueryId = UpdateParameter.getCallbackQueryId(update);
        text = messageText.setChatId(chatId).getText();
        callbackQueryId.ifPresent(s -> toDoList.addToDo(ExecuteMode.POPUP, update, text));

        if (update.hasCallbackQuery()) {

            Optional<String> dialogStep = DialogsState.getDialogStepById(chatId, START_FROM_CALLBACK.getId());
            if (dialogStep.isEmpty()) return toDoList;

            update.getCallbackQuery().setData(dialogStep.get());
            String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
            String identifier;
            if (callbackData != null && callbackData.length > TO.ordinal()) {
                identifier = callbackData[TO.ordinal()];
            } else {
                identifier = MENU.getName();
            }

            toDoList.addAll(callbackContainer.retrieve(identifier).execute(update));
        }

        ResponseWaitingMap.remove(chatId);
        DialogsState.removeAllDialogs(chatId);

        return toDoList;
    }
}
