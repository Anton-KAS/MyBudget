package komrachkov.anton.mybudget.bots.telegram.commands.util;

/**
 * @author Anton Komrachkov
 * @since 0.1
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
