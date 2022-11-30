package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.CancelDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.AbstractCommandControllerTest;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CancelDialogCallback")
public class CancelDialogCallbackTest extends AbstractCallbackTest {
    private static final CommandNames TEST_COMMAND_NAME = ACCOUNTS;

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        DialogsState.removeAllDialogs(AbstractCommandControllerTest.TEST_CHAT_ID);
        DialogsState.put(AbstractCommandControllerTest.TEST_CHAT_ID, TEST_COMMAND_NAME, DialogMapDefaultName.START_FROM_CALLBACK.getId(), ACCOUNTS.getName());
    }

    @Override
    protected String getCommandName() {
        return ACCOUNTS.getName();
    }

    @Override
    public CommandController getCommand() {
        return new CancelDialogCallback(botMessageServiceMock, telegramUserServiceMock, AbstractCommandControllerTest.DEFAULT_EXECUTE_MODE, messageTextMock,
                keyboardMock, callbackContainerMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(CancelDialogText.class);
    }

    @Test
    void shouldProperlyExecuteDialogMapRemove() {
        //given
        DialogsState.put(AbstractCommandControllerTest.TEST_CHAT_ID, TEST_COMMAND_NAME, "Test", "Test");
        Update update = givenUpdate(AbstractCommandControllerTest.TEST_USER_ID, AbstractCommandControllerTest.TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        assertNull(DialogsState.getDialogStateMap(AbstractCommandControllerTest.TEST_CHAT_ID));
    }

    @Test
    void shouldProperlyExecuteDialogMapRemoveExecuteMode() {
        //given
        DialogsState.put(AbstractCommandControllerTest.TEST_CHAT_ID, TEST_COMMAND_NAME, "Test", "Test");
        Update update = givenUpdate(AbstractCommandControllerTest.TEST_USER_ID, AbstractCommandControllerTest.TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        assertNull(DialogsState.getDialogStateMap(AbstractCommandControllerTest.TEST_CHAT_ID));
    }

    @Test
    public void shouldProperlyExecuteSendPopup() {
        //given
        Update update = AbstractCommandControllerTest.getCallbackUpdateWithData(AbstractCommandControllerTest.TEST_DATA);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(botMessageServiceMock, Mockito.times(1)).sendPopup(AbstractCommandControllerTest.TEST_CALLBACK_ID, AbstractCommandControllerTest.TEST_TEXT);
    }

    @Test
    public void shouldProperlyExecuteSendPopupExecuteMode() {
        //given
        Update update = AbstractCommandControllerTest.getCallbackUpdateWithData(AbstractCommandControllerTest.TEST_DATA);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        Mockito.verify(botMessageServiceMock, Mockito.times(1)).sendPopup(AbstractCommandControllerTest.TEST_CALLBACK_ID, AbstractCommandControllerTest.TEST_TEXT);
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUser() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
    }
}
