package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.types.ConditionType;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "date_range_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "date_range_comparison_id"))
})
@JsonTypeName(value = "dateRange")
public class DateRangeComparisonConditionEntity extends ConditionEntity {

    @NotNull
    @Column(name = "date_1")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class,
        QueryJsonView.EditForm.class })
    @Temporal(TemporalType.DATE)
    private Date date1;

    @NotNull
    @Column(name = "date_2")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class,
        QueryJsonView.EditForm.class })
    @Temporal(TemporalType.DATE)
    private Date date2;

    @Column(name = "offset_1")
    @JsonView({ ComplexConditionJsonView.ListComplexConditions.class, QueryJsonView.EditForm.class })
    private Integer offset1;

    public DateRangeComparisonConditionEntity() {
        super();
    }

    @Override
    public String getType() {
        return ConditionType.DateRangeComparison.getValue();
    }

    public DateRangeComparisonConditionEntity(DateRangeComparisonConditionEntity conditionEntity) {
        super(conditionEntity);

        this.date1 = conditionEntity.getDate1();
        this.date2 = conditionEntity.getDate2();
        this.offset1 = conditionEntity.getOffset1();
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Integer getOffset1() {
        return offset1;
    }

    public void setOffset1(Integer offset1) {
        this.offset1 = offset1;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DateRangeComparisonConditionEntity dateRangeComparisonConditionEntity = new DateRangeComparisonConditionEntity();

        dateRangeComparisonConditionEntity.setField1(this.getField1());
        dateRangeComparisonConditionEntity.setOffset1(this.getOffset1());
        dateRangeComparisonConditionEntity.setDate1(this.getDate1());
        dateRangeComparisonConditionEntity.setDate2(this.getDate2());

        return dateRangeComparisonConditionEntity;
    }
}
