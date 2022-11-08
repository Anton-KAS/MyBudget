package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.BANK;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.TYPE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CASH_ID;

public class AddAccountText implements MessageText {
    private final Map<String, String> dialogMap;


    public AddAccountText(Long userId) {
        this.dialogMap = DialogsMap.getDialogMap(userId);
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        AddAccountName[] addAccountNames = AddAccountName.values();
        for (AddAccountName addAccountName : addAccountNames) {
            if (addAccountName.getDialogTextPattern() == null) continue;
            if ((dialogMap.get(TYPE.getDialogId()) == null || dialogMap.get(TYPE.getDialogId()).equals(CASH_ID.getId()))
                    && addAccountName.equals(BANK)) continue;
            String textPattern = dialogMap.get(addAccountName.getDialogIdText());
            if (textPattern == null) {
                stringBuilder.append(String.format(addAccountName.getDialogTextPattern(), count, ""));
            } else {
                stringBuilder.append(String.format(textPattern, count));
            }
            count++;
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n<b>%s</b>");
        return stringBuilder.toString();
    }
}
