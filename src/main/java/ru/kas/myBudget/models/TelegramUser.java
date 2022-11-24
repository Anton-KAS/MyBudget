package ru.kas.myBudget.models;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Entity
@Table(name = "tg_user")
@Getter
@Setter
public class TelegramUser {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "chat_id")
    private long chat_id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "is_premium")
    private Boolean isPremium;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActive;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "telegramUser")
    private List<Account> accounts;

    @Column(name = "last_message_id")
    private Integer lastMessageId;

    @Column(name = "last_message_text")
    private String lastMessageText;

    @Column(name = "last_message_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMessageTimestamp;

    @ManyToOne
    @JoinColumn(name = "web_user_id", referencedColumnName = "id")
    private WebUser webUser;

    public TelegramUser() {
    }

    public TelegramUser(long id, long chat_id, String username, String firstName, String lastName, String languageCode,
                        Boolean isPremium, Date createdAt, Date lastActive, boolean active, WebUser webUser,
                        List<Account> accounts, Integer lastMessageId, String lastMessageText, Date lastMessageTimestamp) {
        this.id = id;
        this.chat_id = chat_id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageCode = languageCode;
        this.isPremium = isPremium;
        this.createdAt = createdAt;
        this.lastActive = lastActive;
        this.active = active;
        this.webUser = webUser;
        this.lastMessageId = lastMessageId;
        this.lastMessageText = lastMessageText;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.accounts = accounts;
    }

    public TelegramUser(Update update) {
        this.id = Integer.parseInt(update.getMessage().getFrom().getId().toString());
        this.chat_id = Integer.parseInt(update.getMessage().getChatId().toString());
        this.username = update.getMessage().getFrom().getUserName();
        this.firstName = update.getMessage().getFrom().getFirstName();
        this.lastName = update.getMessage().getFrom().getLastName();
        this.languageCode = update.getMessage().getFrom().getLanguageCode();
        /*
        if (update.getMessage().getFrom().getIsPremium() == null) {
            this.isPremium = false;
        } else {
            this.isPremium = update.getMessage().getFrom().getIsPremium();
        }
         */
        this.isPremium = update.getMessage().getFrom().getIsPremium();
        this.createdAt = null;
        this.lastActive = null;
        this.active = true;
        this.webUser = null;
        if (update.hasCallbackQuery()) {
            this.lastMessageId = UpdateParameter.getMessageId(update);
            this.lastMessageText = UpdateParameter.getMessageText(update);
            this.lastMessageTimestamp = new Date();
        }
    }

    public void removeLastMessage() {
        this.lastMessageId = null;
        this.lastMessageText = null;
        this.lastMessageTimestamp = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUser that = (TelegramUser) o;
        return id == that.id && chat_id == that.chat_id && username.equals(that.username) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && languageCode.equals(that.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat_id, username, firstName, lastName, languageCode);
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "id=" + id +
                ", chat_id=" + chat_id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", isPremium=" + isPremium +
                ", createdAt=" + createdAt +
                ", lastActive=" + lastActive +
                ", active=" + active +
                ", lastMessageId=" + lastMessageId +
                ", lastMessageText='" + lastMessageText + '\'' +
                ", lastMessageTimestamp=" + lastMessageTimestamp +
                '}';
    }
}
