package org.motechproject.carereporting.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public class IndicatorValueDto {
    private Date date;
    private BigDecimal numerator;
    private BigDecimal denominator;
    private BigDecimal value;

    public IndicatorValueDto(Date date, BigDecimal numerator, BigDecimal denominator, BigDecimal value) {
        this.date = date;
        this.numerator = numerator;
        this.denominator = denominator;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getNumerator() {
        return numerator;
    }

    public BigDecimal getDenominator() {
        return denominator;
    }

    public BigDecimal getValue() {
        return value;
    }
}
