package komrachkov.anton.mybudget.bots.telegram.texts;

import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountsText;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @author Anton Komrachkov
 * @since 0.2
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
