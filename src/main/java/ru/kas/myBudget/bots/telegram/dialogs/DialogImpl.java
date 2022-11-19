package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;

public abstract class DialogImpl implements Dialog {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final MessageText messageText;
    protected Keyboard keyboard;
    //protected final DialogsMap dialogsMap;

    protected Long userId;
    protected Long chatId;
    protected Integer dialogStep;
    protected ExecuteMode defaultExecuteMode;
    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    protected final String askText;

    public DialogImpl(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      MessageText messageText, Keyboard keyboard, String askText) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.messageText = messageText;
        this.keyboard = keyboard;
        //this.dialogsMap = dialogsMap;
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
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        ExecuteMode executeMode = defaultExecuteMode;
        String dialogStepString = DialogsMap.getDialogStepById(chatId, CURRENT_DIALOG_STEP.getId());
        if (dialogStepString != null) {
            dialogStep = Integer.parseInt(dialogStepString);
            executeMode = getExecuteMode(update, dialogStep);
        }
        executeByOrder(update, executeMode);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        executeByOrder(update, executeMode);
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        setData(update);
        executeData(update, executeMode);
    }

    @Override
    public void setData(Update update) {
//        if (userId == null)
//        long chatId = UpdateParameter.getChatId(update);

//        if (dialogStep == null)
//            dialogStep = Integer.parseInt(dialogsMap.getDialogStepById(chatId, CURRENT_DIALOG_STEP.getId()));
//        if (defaultExecuteMode == null) defaultExecuteMode = getExecuteMode(update, dialogStep);

        text = messageText.setUserId(userId).getText();
        inlineKeyboardMarkup = keyboard.getKeyboard();
    }

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode,
                String.format(text, askText), inlineKeyboardMarkup);
    }

    @Override
    public void addToDialogMap(long chatId, CommandDialogNames name, String stringId, String text) {
        DialogsMap.put(chatId, name.getName(), stringId);
        DialogsMap.put(chatId, name.getStepIdText(), text);
    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        if (update.hasCallbackQuery() && dialogStep != null && dialogStep > FIRST_STEP_INDEX.getIndex()) {
            return ExecuteMode.EDIT;
        }
        return ExecuteMode.SEND;
    }
}
