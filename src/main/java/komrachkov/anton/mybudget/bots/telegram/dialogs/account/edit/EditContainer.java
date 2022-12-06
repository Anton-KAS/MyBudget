package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.dialogs.UnknownDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class EditContainer implements DialogStepsContainer {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    @Autowired
    public EditContainer(EditStartDialog editStartDialog, TypeDialog typeDialog, TitleDialog titleDialog,
                         DescriptionDialog descriptionDialog, CurrencyDialog currencyDialog,
                         BankDialog bankDialog, StartBalanceDialog startBalanceDialog,
                         EditConfirmDialog editConfirmDialog, EditSaveDialog editSaveDialog,
                         UnknownDialog unknownDialog) {

        this.unknownDialog = unknownDialog;

        String dialogName = EDIT_ACCOUNT.getName();

        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), editStartDialog.setCurrentDialogName(dialogName))
                .put(TYPE.getName(), typeDialog.setCurrentDialogName(dialogName))
                .put(TITLE.getName(), titleDialog.setCurrentDialogName(dialogName))
                .put(DESCRIPTION.getName(), descriptionDialog.setCurrentDialogName(dialogName))
                .put(CURRENCY.getName(), currencyDialog.setCurrentDialogName(dialogName))
                .put(BANK.getName(), bankDialog.setCurrentDialogName(dialogName))
                .put(START_BALANCE.getName(), startBalanceDialog.setCurrentDialogName(dialogName))
                .put(CONFIRM.getName(), editConfirmDialog.setCurrentDialogName(dialogName))
                .put(SAVE.getName(), editSaveDialog.setCurrentDialogName(dialogName))
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
