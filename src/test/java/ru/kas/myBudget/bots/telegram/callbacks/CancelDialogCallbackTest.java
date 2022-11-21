package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.texts.callback.CancelDialogText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.ACCOUNTS;

@DisplayName("Unit-level testing for CancelDialogCallback")
public class CancelDialogCallbackTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return ACCOUNTS.getName();
    }

    @Override
    public CommandController getCommand() {
        return new CancelDialogCallback(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock,
                keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(CancelDialogText.class);
    }

    @Test
    void shouldProperlyExecuteDialogMapRemove() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(dialogsMapMock, Mockito.times(1)).remove(TEST_USER_ID);
    }

    @Test
    void shouldProperlyExecuteDialogMapRemoveExecuteMode() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(dialogsMapMock, Mockito.times(1)).remove(TEST_USER_ID);
    }
}
