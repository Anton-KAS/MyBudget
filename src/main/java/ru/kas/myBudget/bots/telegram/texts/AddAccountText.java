package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CASH_ID;

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

        AddAccountName[] addAccountNames = AddAccountName.values();
        int n = 0;
        for (int count = 0; count < addAccountNames.length; count++) {
            AddAccountName addAccountName = addAccountNames[count];
            if (addAccountName.getDialogTextPattern() == null) continue;

            if ((dialogMap.get(TYPE.getDialogId()) == null || dialogMap.get(TYPE.getDialogId()).equals(CASH_ID.getId()))
                    && addAccountName.equals(BANK)) continue;

            int currentStepId = Integer.parseInt(dialogMap.get(CURRENT_DIALOG_STEP.getDialogId()));
            if (count != 1 && currentStepId > count) stringBuilder.append("/");
            if (currentStepId == count) stringBuilder.append("<b>");

            String textPattern = dialogMap.get(addAccountName.getDialogIdText());
            if (textPattern == null) {
                stringBuilder.append(String.format(addAccountName.getDialogTextPattern(), n, ""));
            } else {
                stringBuilder.append(String.format(textPattern, n));
            }

            n++;

            if (currentStepId == count) stringBuilder.append("</b>");

            stringBuilder.append("\n");
        }
        stringBuilder.append("\n<b>%s</b>");
        return stringBuilder.toString();
    }
}
