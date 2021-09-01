package com.is.evaluation.model.entity;

import com.is.evaluation.model.entity.base.BaseConfigurationEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "course")
public class Course extends BaseConfigurationEntity {


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private String rating;

    @Column(name = "language")
    private String language;

    @Column(name = "duration")
    private String duration;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> userCours;

    @OneToMany(mappedBy = "course")
    private List<Payment> payments;
}
