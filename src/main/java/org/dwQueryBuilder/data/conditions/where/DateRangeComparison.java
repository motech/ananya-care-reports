package org.dwQueryBuilder.data.conditions.where;

public class DateRangeComparison extends WhereCondition {

    private String date1;
    private String date2;
    private Integer field1Offset;

    public DateRangeComparison() {
        super();
    }

    public DateRangeComparison(String table1Name, String field1Name,
                               String date1, String date2) {
        super(table1Name, field1Name);

        this.date1 = date1;
        this.date2 = date2;
    }

    public DateRangeComparison(String table1Name, String field1Name,
                               String date1, String date2,
                               Integer field1Offset) {
        super(table1Name, field1Name);

        this.date1 = date1;
        this.date2 = date2;
        this.field1Offset = field1Offset;
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

    public Integer getField1Offset() {
        return field1Offset;
    }

    public void setField1Offset(Integer field1Offset) {
        this.field1Offset = field1Offset;
    }
}
