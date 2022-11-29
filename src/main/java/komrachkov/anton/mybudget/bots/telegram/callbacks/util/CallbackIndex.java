package komrachkov.anton.mybudget.bots.telegram.callbacks.util;

import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;

/**
 * Определяет порядок формирования Callback Data.
 * Порядок в списке важен, при обращении к списку используется {@code .ordinal()}
 * Формат Callback Data:
 * <blockquote><pre>
 *          [0]_[1]_[2]_[3]_[4]
 *
 *     1.   TYPE _ FROM _ TO
 *      простой переход по меню
 *     2.   TYPE _ FROM _ TO _ OPERATOR
 *      переход по меню с дополнительным действием без данных
 *     3.   TYPE _ FROM _ TO _ OPERATOR _ OPERATOR_DATA
 *      ереход по меню с доп. данными (например id элемента для поиска)
 *     4.   TYPE _ FROM _ TO _ OPERATOR _ OPERATOR_DATA _ OPERATOR_EXTRA_DATA
 *      переход по меню с двумя дополнительными блоками данных
 *      (например id элемента и номер страницы)
 * </pre></blockquote>
 *
 * <blockquote><pre>
 *     TYPE                 -   NORMAL или DIALOG (see also: {@link CallbackType})
 *     FROM                 -   из какого меню нажата кнопка с callback
 *                          (соделржит {@code CommandNames.getName()} списков интерфейса:
 *                             {@link CommandNames}
 *     TO                   -   в какое меню необходимо произвести переход
 *                          (соделржит {@code CommandNames.getName()} списков интерфейса:
 *                             {@link CommandNames}
 *     OPERATOR             -   какое действие неодходимо выполнить при переходе
 *                          (соделржит {@code .getName()} списка: {@link CallbackOperator}
 *     OPERATOR_DATA        -   данные для выполнения операции
 *     OPERATOR_EXTRA_DATA  -   дополнительные данные для выполнения операции
 * </pre></blockquote>
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

public enum CallbackIndex {
    TYPE,
    FROM,
    TO,
    OPERATION,
    OPERATION_DATA,
    OPERATION_EXTRA_DATA
}
