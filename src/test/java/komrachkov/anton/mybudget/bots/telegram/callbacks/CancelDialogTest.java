package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.dialogs.CancelDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.CancelKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.CancelDialogText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.CancelText;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@DisplayName("Unit-level testing for CancelDialogCallback")
public class CancelDialogTest extends AbstractCallbackTest {
    private static final CommandNames TEST_COMMAND_NAME = ACCOUNTS;

    private static final CancelText cancelTextMock = Mockito.mock(CancelText.class);
    private static final CancelKeyboard cancelKeyboardMock = Mockito.mock(CancelKeyboard.class);

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
        return new CancelDialog(telegramUserServiceMock, cancelTextMock, cancelKeyboardMock, callbackContainerMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(CancelDialogText.class);
    }

    @Test
    void shouldProperlyExecuteDialogMapRemove() {
        //given
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, "Test", "Test");
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update);

        //then
        assertFalse(DialogsState.hasDialogs(TEST_CHAT_ID));
    }

    @Test
    void shouldProperlyExecuteDialogMapRemoveExecuteMode() {
        //given
        DialogsState.put(TEST_CHAT_ID, TEST_COMMAND_NAME, "Test", "Test");
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        getCommand().execute(update, ExecuteMode.EDIT);

        //then
        assertFalse(DialogsState.hasDialogs(TEST_CHAT_ID));
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
