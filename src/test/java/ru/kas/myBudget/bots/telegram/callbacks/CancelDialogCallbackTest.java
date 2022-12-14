package ru.kas.myBudget.bots.telegram.callbacks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.texts.callback.CancelDialogText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;

import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.ACCOUNTS;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CancelDialogCallback")
public class CancelDialogCallbackTest extends AbstractCommandControllerTest {
    private final static CallbackContainer callbackContainerMock = Mockito.mock(CallbackContainer.class);

    @Override
    protected String getCommandName() {
        return ACCOUNTS.getName();
    }

    @Override
    public CommandController getCommand() {
        return new CancelDialogCallback(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock,
                keyboardMock, callbackContainerMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(CancelDialogText.class);
    }

    @Test
    void shouldProperlyExecuteDialogMapRemove() {
        //given
        DialogsMap.put(TEST_CHAT_ID, "Test", "Test");
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        assertNull(DialogsMap.getDialogMap(TEST_CHAT_ID));
    }

    @Test
    void shouldProperlyExecuteDialogMapRemoveExecuteMode() {
        //given
        DialogsMap.put(TEST_CHAT_ID, "Test", "Test");
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        assertNull(DialogsMap.getDialogMap(TEST_CHAT_ID));
    }
}
