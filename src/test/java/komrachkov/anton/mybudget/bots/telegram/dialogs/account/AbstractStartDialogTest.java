package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.START;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AbstractStartDialogTest extends AbstractAccountDialogTest {

    @Override
    protected String getCommandName() {
        return START.getName();
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(AccountDialogText.class);
    }

    @Test
    public void ShouldDoNothingInExecuteOrder() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when - then
        assertDoesNotThrow(() -> getCommand().execute(update, DEFAULT_EXECUTE_MODE));
    }

    @ParameterizedTest
    @MethodSource("sourceStartCommit")
    public void shouldProperlyExecuteCommit(Update update, boolean expected) {
        //when
        boolean result = getCommand().commit(update).isResultCommit();

        //then
        assertEquals(expected, result);
    }

    public static Stream<Arguments> sourceStartCommit() {
        return Stream.of(
                Arguments.of(getUpdateWithText(TEST_TEXT), false),
                Arguments.of(getUpdateWithText(TEST_TEXT), false),

                Arguments.of(getUpdateWithText(TEST_COMMAND), false),
                Arguments.of(getUpdateWithText(TEST_COMMAND), false),

                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),
                Arguments.of(getCallbackUpdateWithData(TEST_TEXT), false),

                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),
                Arguments.of(getCallbackUpdateWithData(TEST_COMMAND), false),

                Arguments.of(getCallbackUpdateWithData(TEST_DATA), true),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), true)
        );
    }

    @Override
    public CommandDialogNames getCommandDialogName() {
        return START;
    }

    @Override
    public void shouldReturnTrueByExecuteCommit(Update update) {
    }

    @Override
    public void shouldProperlyExecuteSetUserId() {
    }

    @Override
    public void shouldProperlyExecuteSetUserIdExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteGetText() {
    }

    @Override
    public void shouldProperlyExecuteGetTextExecuteMode() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUser() {
    }

    @Override
    public void shouldProperlyExecuteAndUpdateUserExecuteMode() {
    }
}
