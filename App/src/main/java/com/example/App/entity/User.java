
package com.example.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserTask> tasks; // ✅ FIXED: Use your actual UserTask entity
}









//package com.example.App.entity;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.scheduling.config.Task;
//import java.util.List;
////
//@Getter
//@Setter
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column
//    private String name;
//    @Column
//    private String email;
//    @Column
//    private String password;
//    @Column
//    private String provider;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Task> tasks;
////    @Column
////    private String provider;
////
////    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<UserTask> tasks;
//
//
//    // Getters & Setters
//}

//public class User {
//
//    // Getters and setters
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id// Auto increment
//    private Long id;
//@Column
//    private String name;
//@Column
//    private String email;
//@Column
//    private String provider;
//
//    // Constructors
//    public User() {}
//
//    public User(String name, String email, String provider) {
//        this.name = name;
//        this.email = email;
//        this.provider = provider;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setProvider(String provider) {
//
//    }
//
//}

