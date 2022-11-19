package ru.kas.myBudget.bots.telegram.texts.addAccountDialog;

import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.texts.MessageText;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;

public class AddAccountText implements MessageText {
    private Long userId;

    public AddAccountText() {
    }

    @Override
    public MessageText setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(userId);

        Map<String, String> dialogMap = DialogsMap.getDialogMap(userId);

        StringBuilder stringBuilder = new StringBuilder();

        AddAccountNames[] addAccountNames = AddAccountNames.values();
        int n = 0;
        for (int count = 0; count < addAccountNames.length; count++) {
            AddAccountNames addAccountName = addAccountNames[count];
            if (addAccountName.getStepTextPattern() == null) continue;

            if ((dialogMap.get(TYPE.getName()) == null || dialogMap.get(TYPE.getName()).equals(CASH_ID.getId()))
                    && addAccountName.equals(BANK)) continue;

            int currentStepId = Integer.parseInt(dialogMap.get(CURRENT_DIALOG_STEP.getId()));
            int lastStepId = Integer.parseInt(dialogMap.get(LAST_STEP.getId()));

            if (currentStepId == count) stringBuilder.append("<b>");

            String textPattern = dialogMap.get(addAccountName.getStepIdText());
            if (count != 0 && (lastStepId > count || textPattern != null)) stringBuilder.append("/");

            if (textPattern == null) {
                stringBuilder.append(String.format(addAccountName.getStepTextPattern(), n, ""));
            } else {
                stringBuilder.append(String.format(textPattern, n));
            }

            n++;

            if (currentStepId == count) stringBuilder.append("</b>  \uD83D\uDC48");

            stringBuilder.append("\n");
        }
        stringBuilder.append("\n<b>%s</b>");
        return stringBuilder.toString();
    }
}
