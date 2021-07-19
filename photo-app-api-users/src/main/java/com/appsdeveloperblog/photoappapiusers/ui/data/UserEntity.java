package com.appsdeveloperblog.photoappapiusers.ui.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(length = 120, nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;

}
