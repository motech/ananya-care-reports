package org.motechproject.carereporting.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    protected static final long serialVersionUID = 0L;

    @Column(name = "creation_date")
    protected Date creationDate;

    @Column(name = "modification_date")
    protected Date modificationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    @PrePersist
    protected void setCreationDate() {
        this.creationDate = new Date();
    }

    @PreUpdate
    protected void setModificationDate() {
        this.modificationDate = new Date();
    }
}
