package ru.kas.myBudget.bots.telegram.commands;

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
