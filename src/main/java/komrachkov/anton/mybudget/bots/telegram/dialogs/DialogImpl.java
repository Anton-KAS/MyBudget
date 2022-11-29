package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogIndex.FIRST_STEP_INDEX;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CONFIRM;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class DialogImpl implements Dialog {
    protected final BotMessageService botMessageService;
    protected final TelegramUserService telegramUserService;
    protected final MessageText messageText;
    protected Keyboard keyboard;

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
        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String dialogStepString = DialogsMap.getDialogStepById(chatId, CURRENT_DIALOG_STEP.getId());
        if (dialogStepString != null) {
            dialogStep = Integer.parseInt(dialogStepString);
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
        if (userId == null) userId = UpdateParameter.getUserId(update);
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

        String dialogStepData = DialogsMap.getDialogStepById(chatId, LAST_STEP.getId());
        if (dialogStepData != null && dialogStepData.equals(String.valueOf(CONFIRM.ordinal()))) {
            DialogsMap.getDialogMap(chatId).replace(CAN_SAVE.getId(), "true");
        }
    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        if (update.hasCallbackQuery() && dialogStep != null && dialogStep > FIRST_STEP_INDEX.ordinal()) {
            return ExecuteMode.EDIT;
        }
        return ExecuteMode.SEND;
    }
}
