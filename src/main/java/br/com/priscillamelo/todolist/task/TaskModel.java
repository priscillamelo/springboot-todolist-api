package br.com.priscillamelo.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks_table")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_tasks_table")
    private UUID id;

    @Column(name = "idUser_tasks_table")
    private UUID idUser;

    @Column(name = "description_tasks_table")
    private String description;

    @Column(name = "title_tasks_table", length = 50)
    private String title;

    @Column(name = "startAt_tasks_table")
    private LocalDateTime startAt;

    @Column(name = "endAt_tasks_table")
    private LocalDateTime endAt;

    @Column(name = "priority_tasks_table")
    private String priority;

    @CreationTimestamp
    @Column(name = "createAt_tasks_table")
    private LocalDateTime createdAt;

}
