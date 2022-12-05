package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StopKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StopText;
import komrachkov.anton.mybudget.models.TelegramUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.STOP;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstractCommandControllerTest {
    private final static StopText stopTextMock = Mockito.mock(StopText.class);
    private final static StopKeyboard stopKeyboardMock = Mockito.mock(StopKeyboard.class);

    @Override
    protected String getCommandName() {
        return STOP.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StopCommand(telegramUserServiceMock, stopTextMock, stopKeyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(StopText.class);
    }

    @Test
    public void shouldProperlyExecuteSetActiveFalse() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setId(TEST_USER_ID);
        Optional<TelegramUser> telegramUserOptional = Optional.of(telegramUser);
        Mockito.when(telegramUserServiceMock.findById(TEST_USER_ID)).thenReturn(telegramUserOptional);
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramUserServiceMock, Mockito.times(1)).save(telegramUser);
    }

    @Test
    public void shouldNotProperlyExecuteSetActiveFalse() {
        // given
        Mockito.when(telegramUserServiceMock.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramUserServiceMock, Mockito.times(0)).save(new TelegramUser());
    }
}
