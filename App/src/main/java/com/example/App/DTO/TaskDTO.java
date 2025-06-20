package com.example.App.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskDTO {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String description;

}
