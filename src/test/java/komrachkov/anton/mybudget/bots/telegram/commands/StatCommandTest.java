package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StatKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StatText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import static komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl.STAT;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest extends AbstractCommandControllerTest {
    private final static StatText statTextMock = Mockito.mock(StatText.class);
    private final static StatKeyboard statKeyboardMock = Mockito.mock(StatKeyboard.class);

    @Override
    protected String getCommandName() {
        return STAT.getName();
    }

    @Override
    public CommandController getCommand() {
        return new StatCommand(telegramUserServiceMock, statTextMock, statKeyboardMock);
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
