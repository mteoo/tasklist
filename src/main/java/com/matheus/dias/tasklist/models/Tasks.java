package com.matheus.dias.tasklist.models;

import com.matheus.dias.tasklist.models.enums.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "The name field is mandatory")
    @Size(min = 1, max = 40, message = "The name field must be between {min} and {max} characters")
    @Column(name = "title")
    private String title;

    @NotNull(message = "The status field is mandatory")
    @Column(name = "status")
    private TaskStatus status = TaskStatus.NEW;

    @Size(max = 500, message = "The description field must have a maximum of {max} characters")
    @Column(name = "description")
    private String description;
}
