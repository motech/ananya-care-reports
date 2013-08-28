package org.dwQueryBuilder.data.enums;

public enum OperatorType {
    Add("+"),
    Divide("/"),
    Multiply("*"),
    Subtract("-");

    private String value;

    OperatorType(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }
}
