package ru.kas.myBudget.bots.telegram.dialogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;

public abstract class AbstractDialogsControllerTest extends AbstractCommandControllerTest {
    protected final String TEST_DIALOG_STEP_ID = "3";
    protected final DialogsMap dialogsMap = Mockito.mock(DialogsMap.class);

    @Override
    public abstract Dialog getCommand();

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        Mockito.when(dialogsMap.getDialogStepById(TEST_USER_ID, CURRENT_DIALOG_STEP.getId())).thenReturn(TEST_DIALOG_STEP_ID);
    }

    @Test
    public void shouldProperlyExecuteCommit() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when
        boolean result = getCommand().commit(update);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldProperlyExecuteSkip() {
        //given
        Update update = givenUpdate(TEST_USER_ID, TEST_CHAT_ID);

        //when - then
        assertDoesNotThrow(() -> getCommand().skip(update));
    }

}
