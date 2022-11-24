package ru.kas.myBudget.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Entity
@Table(name = "web_user")
@Getter
@Setter
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
