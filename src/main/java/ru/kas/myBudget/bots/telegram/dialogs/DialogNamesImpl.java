package ru.kas.myBudget.bots.telegram.dialogs;

import ru.kas.myBudget.bots.telegram.util.CommandNames;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum DialogNamesImpl implements CommandNames {
    ADD_ACCOUNT("addAcc"),
    EDIT_ACCOUNT("editAcc"),
    DELETE_CONFIRM("deleteConfirm"),
    DELETE_EXECUTE("deleteExecute");

    private final String name;

    DialogNamesImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
