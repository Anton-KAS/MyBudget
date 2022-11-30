package komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.util.HashMap;
import java.util.Map;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AccountText implements MessageText {
    private Long userId;

    public AccountText() {
    }

    @Override
    public MessageText setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(userId);

        Map<String, String> dialogMap = DialogsState.getDialogStateMap(userId).orElse(new HashMap<>());

        StringBuilder stringBuilder = new StringBuilder();

        AccountNames[] accountNames = AccountNames.values();
        int n = 0;
        for (int count = 0; count < accountNames.length; count++) {
            AccountNames addAccountName = accountNames[count];
            if (addAccountName.getStepTextPattern() == null) continue;

            if ((dialogMap.get(TYPE.getName()) == null || dialogMap.get(TYPE.getName()).equals(DialogMapDefaultName.CASH_ID.getId()))
                    && addAccountName.equals(BANK)) continue;

            int currentStepId = Integer.parseInt(dialogMap.get(DialogMapDefaultName.CURRENT_DIALOG_STEP.getId()));
            int lastStepId = Integer.parseInt(dialogMap.get(DialogMapDefaultName.LAST_STEP.getId()));

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
