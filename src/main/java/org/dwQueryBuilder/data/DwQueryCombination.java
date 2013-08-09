package org.dwQueryBuilder.data;

import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;

public class DwQueryCombination {
    private CombineType combineType;
    private SimpleDwQuery dwQuery;
    private String foreignKeyFieldName;
    private String referencedFieldName;

    public DwQueryCombination() {

    }

    public DwQueryCombination(CombineType combineType, SimpleDwQuery dwQuery,
                              String foreignKeyFieldName, String referencedFieldName) {
        this.combineType = combineType;
        this.dwQuery = dwQuery;
        this.foreignKeyFieldName = foreignKeyFieldName;
        this.referencedFieldName = referencedFieldName;
    }

    public CombineType getCombineType() {
        return combineType;
    }

    public void setCombineType(CombineType combineType) {
        this.combineType = combineType;
    }

    public SimpleDwQuery getDwQuery() {
        return dwQuery;
    }

    public void setDwQuery(SimpleDwQuery dwQuery) {
        this.dwQuery = dwQuery;
    }

    public String getForeignKeyFieldName() {
        return foreignKeyFieldName;
    }

    public void setForeignKeyFieldName(String foreignKeyFieldName) {
        this.foreignKeyFieldName = foreignKeyFieldName;
    }

    public String getReferencedFieldName() {
        return referencedFieldName;
    }

    public void setReferencedFieldName(String referencedFieldName) {
        this.referencedFieldName = referencedFieldName;
    }
}
