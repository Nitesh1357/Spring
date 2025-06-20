package com.example.App.DTO;
import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String provider;
    private List<TaskDTO> tasks;
}


////
////import jakarta.persistence.Column;
////import jakarta.persistence.Id;
////import lombok.Getter;
////import lombok.Setter;
////
////@Setter
////@Getter
////public class UserDTO {
////
////    @Id
////    private Long id;
////    @Column
////    private String name;
////    @Column
////    private String email;
////
////    public UserDTO() {}
////
////    public UserDTO(Long id, String name, String email) {
////        this.id = id;
////        this.name = name;
////        this.email = email;
////    }
////
////    // Getters and Setters
////}
////
//
//
