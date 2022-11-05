package ru.kas.myBudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.commands.*;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class DialogContainer {

    private final ImmutableMap<String, Command> dialogMap;
    private final Command unknownCommand;

    public DialogContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, CurrencyService currencyService) {
        dialogMap = ImmutableMap.<String, Command>builder()
                .put(ADD_ACCOUNT_TITLE.getDialogName(), new AddAccountTitleDialog(sendBotMessageService, telegramUserService))
                .put(ADD_ACCOUNT_DESCRIPTION.getDialogName(), new AddAccountDescriptionDialog(sendBotMessageService, telegramUserService, currencyService))
                .put(ADD_ACCOUNT_CURRENCY.getDialogName(), new AddAccountDescriptionDialog(sendBotMessageService, telegramUserService, currencyService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveDialog(String dialogIdentifier) {
        return dialogMap.getOrDefault(dialogIdentifier, unknownCommand);
    }
}
