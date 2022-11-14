package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;

public abstract class DialogImpl implements Dialog {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final MessageText messageText;
    protected Keyboard keyboard;
    protected final DialogsMap dialogsMap;

    protected Long userId;
    protected int dialogStep;
    protected ExecuteMode defaultExecuteMode;
    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    protected final String askText;

    public DialogImpl(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap, String askText) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.messageText = messageText;
        this.keyboard = keyboard;
        this.dialogsMap = dialogsMap;
        this.askText = askText;
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }

    @Override
    public void skip(Update update) {
    }

    @Override
    public void execute(Update update) {
        userId = UpdateParameter.getUserId(update);
        dialogStep = Integer.parseInt(dialogsMap.getDialogStepById(userId, CURRENT_DIALOG_STEP.getId()));
        executeByOrder(update, getExecuteMode(update, dialogStep));
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        executeByOrder(update, executeMode);
    }

    protected void executeByOrder(Update update, ExecuteMode executeMode) {
        setData(update);
        executeData(update, executeMode);
    }

    protected void setData(Update update) {
//        userId = UpdateParameter.getUserId(update);
//        dialogStep = Integer.parseInt(dialogsMap.getDialogStepById(userId, CURRENT_DIALOG_STEP.getId()));
        if (userId == null) userId = UpdateParameter.getUserId(update);;
        defaultExecuteMode = getExecuteMode(update, dialogStep);

        text = messageText.setUserId(userId).getText();
        inlineKeyboardMarkup = keyboard.getKeyboard();
    }

    protected void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode,
                String.format(text, askText), inlineKeyboardMarkup);
    }

    protected void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text) {
        Map<String, String> dialogSteps = dialogsMap.getDialogMapById(userId);

        dialogSteps.put(name.getName(), stringId);
        dialogSteps.put(name.getDialogIdText(), text);
    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        if (update.hasCallbackQuery() && dialogStep > FIRST_STEP_INDEX.getIndex()) {
            return ExecuteMode.EDIT;
        }
        return ExecuteMode.SEND;

//        if (update.hasCallbackQuery() && dialogStep == null) {
//            return ExecuteMode.EDIT;
//        } else if (update.hasCallbackQuery() && dialogStep != null) {
//            if (dialogStep > FIRST_STEP_INDEX.getIndex()) {
//                return ExecuteMode.EDIT;
//            }
//        }
//        return ExecuteMode.SEND;
    }
}
