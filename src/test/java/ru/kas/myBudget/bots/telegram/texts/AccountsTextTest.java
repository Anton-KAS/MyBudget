package ru.kas.myBudget.bots.telegram.texts;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kas.myBudget.services.TelegramUserService;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.fail;

public class AccountsTextTest {
    private TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    @Test
    public void ShouldGetError() {
        //given
        MessageText messageText = new AccountsText(telegramUserService);
        String expectedText = "userId is not set";
        String actual = null;

        //when
        try {
            messageText.getText();
        } catch (Error err) {
            actual = err.getMessage();
        }
        //then
        assertEquals("ErrorUserIdSet", expectedText, actual);
    }
}
