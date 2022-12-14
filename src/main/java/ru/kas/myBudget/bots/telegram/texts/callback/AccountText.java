package ru.kas.myBudget.bots.telegram.texts.callback;

import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.AccountService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AccountText implements MessageText {
    private AccountService accountService;
    private Long userId;
    private int accountId;

    public AccountText() {
    }

    public AccountText setAccountService(AccountService accountService) {
        this.accountService = accountService;
        return this;
    }

    public AccountText setAccountId(int accountId) {
        this.accountId = accountId;
        return this;
    }

    @Override
    public MessageText setUserId(Long userId) {
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
