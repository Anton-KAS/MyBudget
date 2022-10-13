package ru.kas.myBudget.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "web_user")
public class WebUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной от 2 до 100 символов")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    /*
    created_at  timestamp           NOT NULL,
    last_active timestamp           NOT NULL,
    active      boolean             NOT NULL,
    tg_user_id  int                 REFERENCES tg_user (id) ON DELETE SET NULL
     */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActive;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "webUser")
    private List<TelegramUser> telegramUsers;

    public WebUser() {
    }

    public WebUser(String username, String password, String role, Date createdAt, Date lastActive, boolean active) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.lastActive = lastActive;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public List<TelegramUser> getTelegramUsers() {
        return telegramUsers;
    }

    public void setTelegramUsers(List<TelegramUser> telegramUsers) {
        this.telegramUsers = telegramUsers;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", lastActive=" + lastActive +
                ", active=" + active +
                '}';
    }
}
