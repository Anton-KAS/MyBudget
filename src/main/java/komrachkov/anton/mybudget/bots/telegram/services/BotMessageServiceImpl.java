package komrachkov.anton.mybudget.bots.telegram.services;

import komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.Logger;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
@Log4j2
public class BotMessageServiceImpl implements BotMessageService {
    private String logStartText = "";
    private final TelegramBot telegramBot;
    private final TelegramUserService telegramUserService;
    public final static int CACHE_TIME = 2;

    public BotMessageServiceImpl(TelegramBot telegramBot, TelegramUserService telegramUserService) {
        this.telegramBot = telegramBot;
        this.telegramUserService = telegramUserService;
    }

    public void executeAndUpdateUser(Update update, ExecuteMode executeMode, String text,
                                     InlineKeyboardMarkup inlineKeyboardMarkup) {
        logStartText = Logger.getLogStartText(update);
        removeInlineKeyboard(update, executeMode);

        if (executeMode == null) return;
        if (text == null) {
            executeMessage(executeMode, update, null, null);
            return;
        }

        Integer sendMessageId = executeMessage(executeMode, update, text, inlineKeyboardMarkup);

        boolean hasMatch = Arrays.stream(text.split("\n")).anyMatch(n -> n.matches(".*/(\\d+) .*"));
        if (inlineKeyboardMarkup == null && !hasMatch) return;

        Optional<TelegramUser> telegramUser = telegramUserService.findById(UpdateParameter.getUserId(update));
        telegramUser.ifPresent(user -> telegramUserService.setLastMessage(user, sendMessageId, text));
    }

    @Override
    public Integer executeMessage(ExecuteMode executeMode, Update update, String message,
                                  InlineKeyboardMarkup inlineKeyboardMarkup) {
        switch (executeMode) {
            case SEND -> {
                return sendMessageDisabledNotification(update, message, inlineKeyboardMarkup);
            }
            case EDIT -> {
                return editMessage(update, message, inlineKeyboardMarkup);
            }
            case DELETE -> {
                return deleteMessage(update);
            }
            case POPUP -> {
                return sendPopup(update, message);
            }
        }
        return null;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.3
     */
    @Override
    public Integer sendMessageDisabledNotification(Update update, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        long chatId = UpdateParameter.getChatId(update);
        SendMessage sendMessage = getSendMessage(chatId, message, inlineKeyboardMarkup);
        sendMessage.setDisableNotification(true);
        return execute(sendMessage, update);
    }

    @Override
    public Integer sendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        if (logStartText.equals("")) logStartText = Logger.getLogStartText(chatId);
        SendMessage sendMessage = getSendMessage(chatId, message, inlineKeyboardMarkup);
        return execute(sendMessage);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.3
     */
    private SendMessage getSendMessage(long chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        if (inlineKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        return sendMessage;
    }

    @Override
    public Integer editMessage(Update update, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        long chatId = UpdateParameter.getChatId(update);
        int messageId = UpdateParameter.getMessageId(update);

        return editMessage(chatId, messageId, message, inlineKeyboardMarkup);
    }

    @Override
    public Integer editMessage(long chatId, int messageId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.enableHtml(true);
        if (inlineKeyboardMarkup != null) editMessage.setReplyMarkup(inlineKeyboardMarkup);
        if (message != null) editMessage.setText(message);

        if (logStartText.equals("")) logStartText = Logger.getLogStartText(chatId);
        return execute(editMessage);
    }

    @Override
    public Integer deleteMessage(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(UpdateParameter.getChatId(update));
        deleteMessage.setMessageId(UpdateParameter.getMessageId(update));

        Optional<TelegramUser> telegramUser = telegramUserService.findById(UpdateParameter.getUserId(update));
        telegramUser.ifPresent(TelegramUser::removeLastMessage);

        return execute(deleteMessage, update);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4
     */
    @Override
    public Integer sendPopup(Update update, String message) {
        Optional<String> callbackQueryId = UpdateParameter.getCallbackQueryId(update);
        if (callbackQueryId.isEmpty()) return null;

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId.get());
        answerCallbackQuery.setText(message);
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setCacheTime(CACHE_TIME);

        return execute(answerCallbackQuery, update);
    }

    @Override
    public void updateUser(Update update) {
        removeInlineKeyboard(update, null);
    }

    private void removeInlineKeyboard(Update update, ExecuteMode executeMode) {
        if (executeMode == ExecuteMode.SEND) executeRemoveInlineKeyboard(update);

        long userId = UpdateParameter.getUserId(update);
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        if (telegramUser == null) return;

        if (executeMode == ExecuteMode.SEND && !update.hasCallbackQuery()) {
            Integer messageIdToRemove = telegramUser.getLastMessageId();
            if (messageIdToRemove == null) return;

            String messageTextToRemove = getMessageTextToRemove(update, telegramUser, messageIdToRemove);
            if (messageTextToRemove == null) return;

            executeRemoveInlineKeyboard(userId, messageIdToRemove, messageTextToRemove);
            telegramUser.removeLastMessage();
        }

        telegramUserService.save(telegramUser);
    }

    private String getMessageTextToRemove(Update update, TelegramUser telegramUser, int messageIdToRemove) {
        if (messageIdToRemove == UpdateParameter.getMessageId(update)) {
            if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage().getText();

            return UpdateParameter.getMessageText(update);
        }
        return telegramUser.getLastMessageText();
    }

    private void executeRemoveInlineKeyboard(Update update) {
        if (!update.hasCallbackQuery()) return;

        String text = update.getCallbackQuery().getMessage().getText();
        executeMessage(ExecuteMode.EDIT, update, cleanTextTags(text), null);
    }

    private void executeRemoveInlineKeyboard(long chatId, int messageId, String messageText) {
        editMessage(chatId, messageId, cleanTextTags(messageText), null);
    }

    private String cleanTextTags(String text) {
        return text.replaceAll("/(\\d+) ", "$1 ");
    }

    @Override
    public Integer execute(BotApiMethod botApiMethod) {
        try {
            Serializable sendMessage = telegramBot.execute(botApiMethod);
            if (sendMessage != null) {
                Integer sendMessageId = getSendMessageId(sendMessage);
                log.info(logStartText + "Executed message id: " + sendMessageId);
                return sendMessageId;
            }
        } catch (TelegramApiRequestException tare) {
            log.warn(logStartText + "TelegramApiRequestException: " + tare.getErrorCode() + " " + tare.getMessage());
        } catch (TelegramApiException tae) {
            log.warn(logStartText + "TelegramApiException: " + Arrays.toString(tae.getStackTrace()));
        }
        log.info(logStartText + "Executed message id: null");
        return null;
    }

    @Override
    public Integer execute(BotApiMethod message, Update update) {
        logStartText = Logger.getLogStartText(update);
        return execute(message);
    }

    private Integer getSendMessageId(Serializable sendMessage) {
        String sendMessageString = sendMessage.toString();

        String PARAMETER = "messageId=";
        int startIndex = sendMessageString.indexOf(PARAMETER);
        if (startIndex == -1) return null;

        startIndex = startIndex + PARAMETER.length();
        int endIndex = sendMessageString.substring(startIndex).indexOf(",");
        if (endIndex == -1) return null;

        endIndex = endIndex + startIndex;
        String sendMessageIdString = sendMessageString.substring(startIndex, endIndex);
        try {
            return Integer.valueOf(sendMessageIdString);
        } catch (NumberFormatException nfe) {
            log.warn(logStartText + "NumberFormatException: " + nfe.getMessage());
        }
        return null;
    }
}
