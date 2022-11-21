package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.commands.StopText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.models.TelegramUser;

import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.STOP;

@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return STOP.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StopCommand(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
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
