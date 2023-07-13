package com.dans.authorization.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "salt")
    private String salt;
    @Column(name = "password")
    private String password;

    public UserEntity(UUID id) {
        this.id = id;
    }

    public UserEntity username(String username) {
        this.username = username;
        return this;
    }

    public UserEntity salt(String salt) {
        this.salt = salt;
        return this;
    }

    public UserEntity password(String password) {
        this.password = password;
        return this;
    }

}
