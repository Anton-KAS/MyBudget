package ru.kas.myBudget.bots.telegram.util;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface Container {
    CommandController retrieve(String identifier);

    boolean contains(String commandNames);
}
