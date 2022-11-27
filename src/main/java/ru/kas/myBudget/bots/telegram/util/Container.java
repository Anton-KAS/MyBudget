package ru.kas.myBudget.bots.telegram.util;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface Container {
    CommandController retrieve(String identifier);

    boolean contains(String commandNames);
}
