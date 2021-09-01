package com.is.evaluation.model.entity.base;

import com.is.evaluation.util.Constants;
import com.is.evaluation.util.Security;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseConfigurationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "deletedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @Column(name = "deletedBy")
    private String deletedBy;

    @Column(name = "lastModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(name = "state")
    private String state;

    @PrePersist
    public void prePersist() {
        createdDate = new Date();
        if (state == null) {
            state = Constants.STATE_ACTIVE;
        }
        if (createdBy == null) {
            createdBy = Security.getUserOfAuthenticatedUser();
        }
    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = new Date();
        lastModifiedBy = Security.getUserOfAuthenticatedUser();
        if (state != null && state.equals(Constants.STATE_DELETED)) {
            deletedDate = lastModifiedDate;
            deletedBy = lastModifiedBy;
        }
    }
}
