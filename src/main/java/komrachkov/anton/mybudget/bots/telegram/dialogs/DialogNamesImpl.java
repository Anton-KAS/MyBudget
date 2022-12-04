package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum DialogNamesImpl implements CommandNames {
    ADD_ACCOUNT("addAcc"),
    EDIT_ACCOUNT("editAcc"),
    CANCEL_DIALOG("cancelDialog"),
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
