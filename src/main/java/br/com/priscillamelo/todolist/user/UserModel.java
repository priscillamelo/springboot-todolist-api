package br.com.priscillamelo.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "users_table")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_users_table")
    private UUID id;

    @Column(name = "name_users_table")
    private String name;

    @Column(name = "username_users_table", unique = true)
    private String username;

    @Column(name = "password_users_table")
    private String password;

    @CreationTimestamp
    @Column(name = "createdAt_users_table")
    private LocalDateTime createdAt;

}
