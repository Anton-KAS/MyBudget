package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AbstractStartDialogTest;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountKeyboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;

import java.util.stream.Stream;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@DisplayName("Unit-level testing for EditAccountSaveDialog")
public class EditStartDialogTest extends AbstractStartDialogTest {
    private final static String EXPECTED_TRUE_CALLBACK = String.format(AccountKeyboard.EDIT_ACCOUNT_BUTTON_PATTERN, TEST_ACCOUNT_ID);

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(accountMock.getAccountType()).thenReturn(accountTypeMock);
        Mockito.when(accountMock.getCurrency()).thenReturn(currencyMock);
    }

    @Override
    public Dialog getCommand() {
        return new EditStartDialog(telegramUserServiceMock, accountTextMock, accountServiceMock);
    }

    @Override
    protected String getTestDialogName() {
        return EDIT_ACCOUNT.getName();
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

                Arguments.of(getCallbackUpdateWithData(TEST_DATA), false),
                Arguments.of(getCallbackUpdateWithData(TEST_DATA), false),

                Arguments.of(getCallbackUpdateWithData(EXPECTED_TRUE_CALLBACK), true),
                Arguments.of(getCallbackUpdateWithData(EXPECTED_TRUE_CALLBACK), true)
        );
    }
}
