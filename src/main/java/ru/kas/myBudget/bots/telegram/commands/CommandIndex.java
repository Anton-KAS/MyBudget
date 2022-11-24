package ru.kas.myBudget.bots.telegram.commands;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

public enum CommandIndex {
    COMMAND(0);

    private final int index;

    CommandIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
