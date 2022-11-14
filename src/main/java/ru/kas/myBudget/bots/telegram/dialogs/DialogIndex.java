package ru.kas.myBudget.bots.telegram.dialogs;

public enum DialogIndex {
    FIRST_STEP_INDEX(0),
    CALLBACK_STEP_INDEX(3),
    CALLBACK_OPERATION_DATA_INDEX(4);

    private final int index;


    DialogIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
