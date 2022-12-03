package komrachkov.anton.mybudget.bots.telegram.commands.util;

import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface ContainerTest {
    CommandControllerTest retrieve(String identifier);

    boolean contains(String commandNames);
}
