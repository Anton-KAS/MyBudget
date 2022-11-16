package ru.kas.myBudget.bots.telegram.commands;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import ru.kas.myBudget.bots.telegram.texts.HelpText;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.AbstractCommandControllerTest;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl.HELP;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandControllerTest {
    @Override
    protected String getCommandName() {
        return HELP.getName();
    }

    @Override
    public CommandController getCommand() {
        return new HelpCommand(botMessageServiceMock, telegramUserServiceMock, DEFAULT_EXECUTE_MODE, messageTextMock, keyboardMock);
    }

    @Override
    public MessageText getMockMessageText() {
        return Mockito.mock(HelpText.class);
    }
}
