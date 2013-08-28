package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;

public class DateRangeComparison extends WhereCondition {

    private String date1;
    private String date2;
    private Integer column1Offset;

    public DateRangeComparison() {
        super();
    }

    public DateRangeComparison(String table1Name, String field1Name,
                               String date1, String date2) {
        super(table1Name, field1Name);

        this.date1 = date1;
        this.date2 = date2;
    }

    public DateRangeComparison(SelectColumn selectColumn1,
                               String date1, String date2) {
        super(selectColumn1);

        this.date1 = date1;
        this.date2 = date2;
    }

    public DateRangeComparison(String table1Name, String field1Name,
                               String date1, String date2,
                               Integer column1Offset) {
        super(table1Name, field1Name);

        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = column1Offset;
    }

    public DateRangeComparison(SelectColumn selectColumn1,
                               String date1, String date2,
                               Integer column1Offset) {
        super(selectColumn1);

        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = column1Offset;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public Integer getColumn1Offset() {
        return column1Offset;
    }

    public void setColumn1Offset(Integer column1Offset) {
        this.column1Offset = column1Offset;
    }
}
