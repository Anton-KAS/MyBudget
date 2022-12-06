package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.dialogs.UnknownDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AddContainer implements DialogStepsContainer {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    @Autowired
    public AddContainer(AddStartDialog addStartDialog, TypeDialog typeDialog, TitleDialog titleDialog,
                        DescriptionDialog descriptionDialog, CurrencyDialog currencyDialog,
                        BankDialog bankDialog, StartBalanceDialog startBalanceDialog,
                        AddConfirmDialog addConfirmDialog, AddSaveDialog addSaveDialog,
                        UnknownDialog unknownDialog) {

        this.unknownDialog = unknownDialog;

        String dialogName = ADD_ACCOUNT.getName();

        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), addStartDialog.setCurrentDialogName(dialogName))
                .put(TYPE.getName(), typeDialog.setCurrentDialogName(dialogName))
                .put(TITLE.getName(), titleDialog.setCurrentDialogName(dialogName))
                .put(DESCRIPTION.getName(), descriptionDialog.setCurrentDialogName(dialogName))
                .put(CURRENCY.getName(), currencyDialog.setCurrentDialogName(dialogName))
                .put(BANK.getName(), bankDialog.setCurrentDialogName(dialogName))
                .put(START_BALANCE.getName(), startBalanceDialog.setCurrentDialogName(dialogName))
                .put(CONFIRM.getName(), addConfirmDialog.setCurrentDialogName(dialogName))
                .put(SAVE.getName(), addSaveDialog.setCurrentDialogName(dialogName))
                .build();

    }

    @Override
    public Dialog retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }

    @Override
    public boolean contains(String commandNames) {
        return dialogMap.containsKey(commandNames);
    }
}
