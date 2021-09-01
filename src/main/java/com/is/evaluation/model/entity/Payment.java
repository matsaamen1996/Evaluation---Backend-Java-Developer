package com.is.evaluation.model.entity;

import com.is.evaluation.model.entity.base.BaseConfigurationEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "payment")
public class Payment extends BaseConfigurationEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "totalAmount")
    private String totalAmount;

    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private String amount;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "payment")
    private List<Enrollment> userCours;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId")
    private Course course;
}
