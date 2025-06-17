package com.nkm.Testapp.DTO;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private List<TaskDTO> tasks;
}

