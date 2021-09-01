package com.is.evaluation.model.entity;

import com.is.evaluation.model.entity.base.BaseConfigurationEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User extends BaseConfigurationEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "gender")
    private String gender;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "language")
    private String language;

    @Column(name = "education")
    private String education;

    @Column(name = "completed")
    private Boolean completed;
    


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="user_rol",
            joinColumns = @JoinColumn(name="userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments;

}
