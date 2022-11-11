package ru.kas.myBudget.bots.telegram.callbacks;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.commands.MenuCommand;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MenuText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateExtraction;
import ru.kas.myBudget.services.*;

import java.awt.*;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class CallbackContainer implements Container {

    private final ImmutableMap<String, UpdateExtraction> callbackMap;
    private final Callback unknownCommand;
    private final static ExecuteMode defaultExecuteMode = ExecuteMode.EDIT;

    public CallbackContainer(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        callbackMap = ImmutableMap.<String, UpdateExtraction>builder()
                .put(ACCOUNTS.getCallbackName(),
                        new AccountsCallback(defaultExecuteMode, botMessageService, telegramUserService))
                .put(MENU.getCallbackName(),
                        new MenuCommand(defaultExecuteMode, botMessageService, telegramUserService,
                                new MenuKeyboard(), new MenuText(telegramUserService)))
                .put(CLOSE.getCallbackName(),
                        new CloseCallback(botMessageService, telegramUserService))
                .put(NO.getCallbackName(),
                        new NoCallback(defaultExecuteMode, botMessageService))
                .put(CANCEL_DIALOG.getCallbackName(),
                        new CancelCallbackDialog(botMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCallback(defaultExecuteMode, botMessageService);
    }

    @Override
    public UpdateExtraction retrieve(String identifier) {
        return callbackMap.getOrDefault(identifier, unknownCommand);
    }
}
