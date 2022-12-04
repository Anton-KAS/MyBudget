package komrachkov.anton.mybudget.bots.telegram.texts.callback;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.NoText;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AccountText implements MessageText {
    private final AccountService accountService;
    private Long userId;
    private int accountId;

    @Autowired
    public AccountText(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountText setAccountId(int accountId) {
        this.accountId = accountId;
        return this;
    }

    @Override
    public MessageText setChatId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        Account account = accountService.findById(accountId).orElse(null);
        if (account == null) return new NoText().getText();

        Bank bank = account.getBank();
        String bankText = "";
        if (bank != null) {
            bankText = "\n" + bank.displayToUser();
        }

        String description = account.getDescription();
        if (description == null) description = "";
        else description = "\n" + description;

        String pattern = """
                <b>%s</b>%s
                                
                %s<i>%s</i>
                                
                Баланс: <b>%s %s</b>
                """;

        return String.format(pattern,
                account.getTitle(),
                bankText,
                account.getAccountType().getTitleRu(),
                description,
                account.getCurrentBalanceWithScale(), account.getCurrency().getSymbol()
        );
    }
}
