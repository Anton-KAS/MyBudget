package ru.kas.myBudget.bots.telegram.texts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountsText;
import ru.kas.myBudget.services.TelegramUserService;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for AccountsText")
public class AccountsTextTest {
    private final TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);

    @Test
    public void ShouldGetError() {
        //given
        MessageText messageText = new AccountsText(telegramUserServiceMock);
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
