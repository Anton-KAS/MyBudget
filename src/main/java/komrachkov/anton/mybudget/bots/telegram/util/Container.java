package komrachkov.anton.mybudget.bots.telegram.util;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface Container {
    CommandController retrieve(String identifier);

    boolean contains(String commandNames);
}
