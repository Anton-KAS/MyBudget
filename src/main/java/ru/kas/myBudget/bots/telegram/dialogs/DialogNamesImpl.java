package ru.kas.myBudget.bots.telegram.dialogs;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

public enum DialogNamesImpl implements CommandNames {
    ADD_ACCOUNT("addAcc"),
    EDIT_ACCOUNT("editAcc");

    private final String name;

    DialogNamesImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
