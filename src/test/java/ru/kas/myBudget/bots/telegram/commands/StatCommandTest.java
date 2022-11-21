package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.commands.StatText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.STAT;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return STAT.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StatCommand(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        StatText statText = Mockito.mock(StatText.class);
        Mockito.when(statText.setActiveUserCount(TEST_USER_LIST_SIZE)).thenReturn(statText);
        return statText;
    }

    @Test
    public void shouldProperlyExecuteRetrieveAllActiveUsers() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(telegramUserServiceMock, Mockito.times(1)).retrieveAllActiveUsers();
    }
    @Test
    public void shouldProperlyExecuteRetrieveAllActiveUsersExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(telegramUserServiceMock, Mockito.times(1)).retrieveAllActiveUsers();
    }
}
