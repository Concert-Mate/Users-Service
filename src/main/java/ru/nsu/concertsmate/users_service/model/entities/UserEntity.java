package ru.nsu.concertsmate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Column(name = "telegram_id", unique = true, nullable = false)
    private long telegramId;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDatetime;

    public UserEntity(long telegramId) {
        this.telegramId = telegramId;
        this.creationDatetime = new Date();
    }
}
