package komrachkov.anton.mybudget.bots.telegram.texts.callback;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AccountsText implements MessageText {
    private final TelegramUserService telegramUserService;
    private Long userId;
    private final static String TEXT_FORMAT =
            """
                    📚  <b>Счета:</b>
                    %s%s
                    """;

    private final static String TEXT_TOTAL = "\n\n<b>Общий баланс: <i>%s</i></b>";

    public AccountsText(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @Override
    public MessageText setChatId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(userId);
        Optional<TelegramUser> telegramUser = telegramUserService.findById(userId);
        if (telegramUser.isEmpty()) return String.format(TEXT_FORMAT, "Что-то пошло не так (((", "");

        List<Account> accounts = telegramUser.get().getAccounts();
        if (accounts.isEmpty()) return String.format(TEXT_FORMAT, "Нет счетов", "");

        StringBuilder accountTextBuilder = new StringBuilder();
        for (int n = 0; n < accounts.size(); n++) {
            Account account = accounts.get(n);
            accountTextBuilder
                    .append("\n").append("/").append(n + 1).append(" - ")
                    .append(account.getTitle())
                    .append(" : ").append("<i><b>")
                    .append(account.getCurrentBalanceWithScale())
                    .append(" ")
                    .append(account.getCurrency().getSymbol()).append("</b></i>");
        }

        String textTotal = String.format(TEXT_TOTAL, ""); //TODO: Add count total balance of accounts
        return String.format(TEXT_FORMAT, accountTextBuilder, textTotal);
    }
}
