package komrachkov.anton.mybudget.bots.telegram.util;

import komrachkov.anton.mybudget.services.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

abstract public class AbstractContainerTest {
    protected Container container;
    protected CommandNames[] commandNames;
    protected CommandController unknownCommand;

    protected TelegramUserService telegramUserServiceMock = Mockito.mock(TelegramUserService.class);

    protected static String TEST_UNKNOWN_COMMAND = "/testUnknownCommand";

    protected abstract void setContainer();

    protected abstract void setNames();

    protected abstract void setUnknownCommand();

    @BeforeEach
    public void beforeEach() {
        init();
    }

    protected void init() {
        setContainer();
        setUnknownCommand();
        setNames();
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(commandNames).forEach(commandName -> {
            CommandController command = container.retrieve(commandName.getName());
            System.out.println("Command name: " + commandName.getName() + " - " + !unknownCommand.getClass().equals(command.getClass()));
            Assertions.assertNotEquals(unknownCommand.getClass(), command.getClass());
        });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //when
        CommandController command = container.retrieve(TEST_UNKNOWN_COMMAND);

        //then
        Assertions.assertEquals(unknownCommand.getClass(), command.getClass());
    }
}
