package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogPattern;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.StartBalanceKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.services.CurrencyService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class StartBalanceDialog extends DialogImpl {
    protected final CurrencyService currencyService;
    protected final static String ASK_TEXT = "Введите текущий баланс счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Введите только одно число";
    protected final static String DEFAULT_BALANCE_TEXT = "0.0";

    @Autowired
    public StartBalanceDialog(TelegramUserService telegramUserService, AccountDialogText messageText, StartBalanceKeyboard keyboard,
                              CurrencyService currencyService) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
        this.currencyService = currencyService;
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();

        String receivedText = UpdateParameter.getMessageText(update);
        if (update.hasCallbackQuery() || !receivedText.matches(DialogPattern.CURRENCY_AMOUNT.getRegex())) {
            toDoList.addToDo(ExecuteMode.SEND, update, VERIFY_EXCEPTION_TEXT);
            return toDoList;
        }

        long chatId = UpdateParameter.getChatId(update);
        BigDecimal startBalance = getStartBalance(receivedText, chatId);

        addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                String.format(START_BALANCE.getStepTextPattern(), "%s", startBalance));

        toDoList.setResultCommit(true);
        return toDoList;
    }

    @Override
    public ToDoList skip(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        BigDecimal startBalance = getStartBalance(DEFAULT_BALANCE_TEXT, chatId);

        Optional<String> startBalanceOpt = DialogsState.getByStepId(chatId, START_BALANCE.getName());

        if (startBalanceOpt.isEmpty()) {
            addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                    String.format(START_BALANCE.getStepTextPattern(), "%s", startBalance));
        }
        return new ToDoList();
    }

    private BigDecimal getStartBalance(String text, long chatId) {
        text = text.replace(",", ".");
        BigDecimal startBalance = new BigDecimal(text);

        Optional<String> currencyOpt = DialogsState.getByStepId(chatId, CURRENCY.getName());
        int numberToBAsic;
        if (currencyOpt.isPresent()) {
            Optional<Currency> currency = currencyService.findById(Integer.parseInt(currencyOpt.get()));
            numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);
        } else {
            numberToBAsic = 1;
        }

        return startBalance.setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);
    }
}
