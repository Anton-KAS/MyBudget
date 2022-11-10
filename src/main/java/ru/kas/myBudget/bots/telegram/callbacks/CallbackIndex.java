package ru.kas.myBudget.bots.telegram.callbacks;

public enum CallbackIndex {
    TYPE(0),
    FROM(1),
    TO(2);

    private final int index;

    CallbackIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}