package ru.kas.myBudget.bots.telegram.dialogs.account;

import ru.kas.myBudget.bots.telegram.dialogs.AbstractDialogImplTest;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.texts.MessageText;

public abstract class AbstractAccountDialogTest extends AbstractDialogImplTest {
    protected final static int TEST_CURRENCY_ID = 11111;
    protected final static int TEST_TYPE_ACCOUNT_ID = 22222;
    protected final static int TEST_BANK_ID = 33333;
    protected final static int TEST_ACCOUNT_ID = 44444;

    @Override
    protected abstract CommandDialogNames getCommandDialogName();

    @Override
    protected abstract String getCommandName();

    @Override
    protected abstract Dialog getCommand();

    @Override
    protected abstract MessageText getMockMessageText();
}
