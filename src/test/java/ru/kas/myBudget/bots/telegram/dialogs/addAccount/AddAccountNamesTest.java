package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class AddAccountNamesTest {
    @Test
    public void orderTest() {
        //given
        AddAccountNames[] expectedOrder = {
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
        AddAccountNames[] resultOrder = AddAccountNames.values();

        //when
        assertArrayEquals(expectedOrder, resultOrder);
    }
}
