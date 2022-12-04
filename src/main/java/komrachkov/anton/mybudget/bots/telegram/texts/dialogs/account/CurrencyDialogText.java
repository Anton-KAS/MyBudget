package komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account;

/**
 * @author Anton Komrachkov
 * @since 0.4 (03.12.2022)
 */

public class CurrencyDialogText extends AccountDialogText {

    @Override
    public String getText() {
        String text = super.getText();
        return text + "\n<i>или введите название для поиска в списке</i>";
    }
}
