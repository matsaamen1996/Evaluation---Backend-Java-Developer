package com.is.evaluation.model.entity;

import com.is.evaluation.model.entity.base.BaseConfigurationEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role extends BaseConfigurationEntity {
    @Column(name = "role")
    private String role;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;


}
