package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.StopText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.models.TelegramUser;

import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.commands.CommandName.STOP;

@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    public CommandController getCommand() {
        return new StopCommand(botMessageService, telegramUserService, DEFAULT_EXECUTE_MODE, messageText, keyboard);
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
        Mockito.when(telegramUserService.findById(TEST_USER_ID)).thenReturn(telegramUserOptional);
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramUserService, Mockito.times(1)).save(telegramUser);
    }

    @Test
    public void shouldNotProperlyExecuteSetActiveFalse() {
        // given
        Mockito.when(telegramUserService.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramUserService, Mockito.times(0)).save(new TelegramUser());
    }
}
