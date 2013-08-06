package org.motechproject.carereporting.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.dto.MessageDto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Table(name = "language")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "language_id"))
})
public class LanguageEntity extends AbstractEntity {

    @NotEmpty
    @Column(name = "code")
    private String code;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Transient
    private Set<MessageDto> messages;

    public LanguageEntity() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDto> messages) {
        this.messages = messages;
    }
}
