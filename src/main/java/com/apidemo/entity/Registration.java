package com.apidemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//lonbok annotations Also we can use @Data from lombok it will give you both getter and setter together
@Getter
@Setter
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "mobile", nullable = false, unique = true)
    private String mobile;

}