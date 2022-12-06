package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.DialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import java.util.Arrays;
import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex.FIRST_STEP_INDEX;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class DialogImpl implements Dialog {
    protected final TelegramUserService telegramUserService;
    protected final MessageText messageText;
    protected DialogKeyboard keyboard;

    protected String dialogName;
    protected Integer dialogStep;
    protected ExecuteMode defaultExecuteMode;
    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    protected final String askText;

    public DialogImpl(TelegramUserService telegramUserService,
                      MessageText messageText, DialogKeyboard keyboard, String askText) {
        this.telegramUserService = telegramUserService;
        this.messageText = messageText;
        this.keyboard = keyboard;
        this.askText = askText;
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();
        toDoList.setResultCommit(true);
        return toDoList;
    }

    @Override
    public ToDoList skip(Update update) {
        return new ToDoList();
    }

    @Override
    public ToDoList execute(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        Optional<String> dialogStepString = DialogsState.getDialogStepById(chatId, CURRENT_DIALOG_STEP.getId());
        dialogStepString.ifPresent(s -> dialogStep = Integer.parseInt(s));

        if (dialogStep == null) dialogStep = getDialogStepFromCallback(update);
        ExecuteMode executeMode = autoDefineExecuteMode(update, dialogStep);

        return execute(update, executeMode);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);
        text = String.format(messageText.setChatId(chatId).getText(), askText);
        if (keyboard != null) inlineKeyboardMarkup = keyboard.getKeyboard();

        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
        return toDoList;
    }

    @Override
    public void setDefaultExecuteMode(Update update) {
        this.defaultExecuteMode = autoDefineExecuteMode(update, dialogStep);
    }

    @Override
    public ExecuteMode autoDefineExecuteMode(Update update, Integer dialogStep) {
        if (update.hasCallbackQuery() && dialogStep != null && dialogStep > FIRST_STEP_INDEX.ordinal()) {
            return ExecuteMode.EDIT;
        }
        return ExecuteMode.SEND;
    }

    @Override
    public void addToDialogMap(long chatId, CommandDialogNames name, String stringId, String text) {
        DialogsState.put(chatId, name, name.getName(), stringId);
        DialogsState.put(chatId, name, name.getStepIdText(), text);

        Optional<String> dialogStepData = DialogsState.getDialogStepById(chatId, LAST_STEP.getId());

        if (dialogStepData.isPresent() && dialogStepData.get().equals(String.valueOf(CONFIRM.ordinal()))) {
            DialogsState.replaceById(chatId, CAN_SAVE.getId(), "true");
        }
    }

    @Override
    public Dialog setCurrentDialogName(String dialogName) {
        this.dialogName = dialogName;
        if (keyboard != null) keyboard.setDialogName(dialogName);
        return this;
    }

    protected Integer getDialogStepFromCallback(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null) return null;
        if (callbackData.length < OPERATION.ordinal()) return null;
        String stepId = callbackData[OPERATION.ordinal()];
        AccountNames accountName = Arrays.stream(AccountNames.values()).filter(s -> s.getName().equals(stepId)).findAny().orElse(null);
        if (accountName == null) return null;
        return accountName.ordinal();
    }
}
