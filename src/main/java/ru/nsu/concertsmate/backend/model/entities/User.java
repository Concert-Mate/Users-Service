package ru.nsu.concertsmate.backend.model.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id", unique = true, nullable = false)
    private long telegramId;

    public User(Long telegramId) {
        this.telegramId = telegramId;
    }
}
