package ru.kas.myBudget.models;

import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tg_user")
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
    private Long lastMessageId;

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
                        List<Account> accounts, Long lastMessageId, String lastMessageText, Date lastMessageTimestamp) {
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
            this.lastMessageId = Long.valueOf(update.getCallbackQuery().getMessage().getMessageId());
            this.lastMessageText = update.getCallbackQuery().getMessage().getText();
            this.lastMessageTimestamp = new Date();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public Long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Long lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public Date getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Date lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public void removeLastMessage() {
        this.lastMessageId = null;
        this.lastMessageText = null;
        this.lastMessageTimestamp = null;
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
