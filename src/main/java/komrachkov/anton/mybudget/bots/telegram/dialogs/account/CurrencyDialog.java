package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.CurrenciesKeyboard;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.CurrencyService;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.PAGE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class CurrencyDialog extends DialogImpl {
    private final CurrencyService currencyService;
    private final static int PAGE_INDEX = 5;
    private final static String ASK_TEXT = "Выберете валюту счета:";
    private final CurrenciesKeyboard currenciesKeyboard;

    @Autowired
    public CurrencyDialog(TelegramUserService telegramUserService, AccountDialogText messageText, CurrenciesKeyboard keyboard,
                          CurrencyService currencyService) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
        this.currenciesKeyboard = keyboard;
        this.currencyService = currencyService;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        setKeyboardPage(update);
        currenciesKeyboard.setUserId(UpdateParameter.getUserId(update));

        ToDoList toDoList = new ToDoList();
        String searchWord = UpdateParameter.getMessageText(update);
        if (!update.hasCallbackQuery() && !searchWord.contains(COMMAND_PREFIX)) {
            text = messageText.setChatId(UpdateParameter.getChatId(update)).getText();
            inlineKeyboardMarkup = currenciesKeyboard.getKeyboard(searchWord.toLowerCase());
            toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
            return toDoList;
        } else return super.execute(update, executeMode);
    }

    @Override
    public ToDoList commit(Update update) {
        long chatId = UpdateParameter.getUserId(update);
        ToDoList toDoList = new ToDoList();

        if (update.hasMessage() && !update.hasCallbackQuery()) {
            String searchWord = UpdateParameter.getMessageText(update);
            if (searchWord.length() != 0) {
                return toDoList;
            }
        }

        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null ||
                (update.hasCallbackQuery() &&
                        callbackData.length > PAGE_INDEX &&
                        callbackData[PAGE_INDEX - 1].equals(PAGE.getId())))
            return toDoList;

        int currencyId;
        if (callbackData.length > OPERATION_DATA.ordinal())
            currencyId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
        else return toDoList;

        Optional<Currency> currency = currencyService.findById(currencyId);
        if (currency.isEmpty()) return toDoList;

        String text = String.format(CURRENCY.getStepTextPattern(),
                "%s", currency.get().getSymbol() + " - " + currency.get().getCurrencyRu());

        addToDialogMap(chatId, CURRENCY, String.valueOf(currencyId), text);

        toDoList.setResultCommit(true);
        return toDoList;
    }

    private void setKeyboardPage(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        int page;
        if (update.hasCallbackQuery() && callbackData != null && callbackData.length > PAGE_INDEX
                && callbackData[PAGE_INDEX - 1].equals(PAGE.getId())) {
            page = Integer.parseInt(callbackData[PAGE_INDEX]);
        } else {
            page = 1;
        }
        currenciesKeyboard.setPage(page);
    }
}
