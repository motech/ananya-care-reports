package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.views.QueryJsonView;

public class GroupByDto {

    @JsonView({ QueryJsonView.EditForm.class })
    private ComputedFieldEntity computedField;

    @JsonView({ QueryJsonView.EditForm.class })
    private HavingDto having;

    public GroupByDto() {

    }

    public GroupByDto(ComputedFieldEntity computedField, HavingDto having) {
        this.computedField = computedField;
        this.having = having;
    }

    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    public HavingDto getHaving() {
        return having;
    }

    public void setHaving(HavingDto having) {
        this.having = having;
    }
}
