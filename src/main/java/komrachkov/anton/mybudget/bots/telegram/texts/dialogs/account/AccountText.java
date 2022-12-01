package komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.CURRENT_DIALOG_STEP;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.LAST_STEP;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AccountText implements MessageText {
    private Long chatId;

    public AccountText() {
    }

    @Override
    public MessageText setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    @Override
    public String getText() {
        checkUserIdSet(chatId);

        StringBuilder stringBuilder = new StringBuilder();

        AccountNames[] accountNames = AccountNames.values();
        int n = 0;
        for (int count = 0; count < accountNames.length; count++) {
            AccountNames addAccountName = accountNames[count];
            if (addAccountName.getStepTextPattern() == null) continue;

            Optional<String> typeOpt = DialogsState.getByStepId(chatId, TYPE.getName());
            if ((typeOpt.isEmpty() || typeOpt.get().equals(DialogMapDefaultName.CASH_ID.getId()))
                    && addAccountName.equals(BANK)) continue;

            int currentStepId = Integer.parseInt(DialogsState.getByStepId(chatId, CURRENT_DIALOG_STEP.getId()).orElse("0"));
            int lastStepId = Integer.parseInt(DialogsState.getByStepId(chatId, LAST_STEP.getId()).orElse("0"));

            if (currentStepId == count) stringBuilder.append("<b>");

            Optional<String> textPattern = DialogsState.getByStepId(chatId, addAccountName.getStepIdText());
            if (count != 0 && (lastStepId > count || textPattern.isPresent())) stringBuilder.append("/");

            if (textPattern.isEmpty()) stringBuilder.append(String.format(addAccountName.getStepTextPattern(), n, ""));
            else stringBuilder.append(String.format(textPattern.get(), n));

            if (currentStepId == count) stringBuilder.append("</b>  \uD83D\uDC48");
            stringBuilder.append("\n");
            n++;
        }
        stringBuilder.append("\n<b>%s</b>");
        return stringBuilder.toString();
    }
}
