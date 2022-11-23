package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.Test;
import ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;

public class AccountNamesTest {
    @Test
    public void orderTest() {
        //given
        AccountNames[] expectedOrder = {
                START,
                TYPE,
                TITLE,
                DESCRIPTION,
                CURRENCY,
                BANK,
                START_BALANCE,
                CONFIRM,
                SAVE
        };

        //then
        AccountNames[] resultOrder = AccountNames.values();

        //when
        assertArrayEquals(expectedOrder, resultOrder);
    }
}
