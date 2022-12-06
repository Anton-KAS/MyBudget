package komrachkov.anton.mybudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddAccountDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit.EditAccountDialog;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class DialogContainer implements Container {
    private final ImmutableMap<String, CommandController> dialogMap;
    private final CommandController unknownDialog;

    @Autowired
    public DialogContainer(UnknownDialog unknownDialog, DeleteConfirmDialog deleteConfirmDialog,
                           DeleteExecuteDialog deleteExecuteDialog, AddAccountDialog addAccountDialog,
                           EditAccountDialog editAccountDialog, CancelDialog cancelDialog) {

        this.unknownDialog = unknownDialog;

        dialogMap = ImmutableMap.<String, CommandController>builder()
                .put(DELETE_CONFIRM.getName(), deleteConfirmDialog)
                .put(DELETE_EXECUTE.getName(), deleteExecuteDialog)
                .put(ADD_ACCOUNT.getName(), addAccountDialog)
                .put(EDIT_ACCOUNT.getName(), editAccountDialog)
                .put(CANCEL_DIALOG.getName(), cancelDialog)
                .build();

    }

    @Override
    public CommandController retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }

    @Override
    public boolean contains(String commandNames) {
        return dialogMap.containsKey(commandNames);
    }
}
