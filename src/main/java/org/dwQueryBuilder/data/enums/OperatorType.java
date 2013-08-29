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

    public static OperatorType fromName(String name) {
        switch (name) {
            case "ADD":
                return Add;
            case "DIV":
                return Divide;
            case "MUL":
                return Multiply;
            case "SUB":
                return Subtract;
            default:
                throw new IllegalArgumentException("Operator name: " + name  + " not supported.");
        }
    }
}
