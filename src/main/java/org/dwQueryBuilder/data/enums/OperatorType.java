package org.dwQueryBuilder.data.enums;

public enum OperatorType {
    Less,
    LessEqual,
    Equal,
    NotEqual,
    Greater,
    GreaterEqual;

    public static OperatorType fromSymbol(String symbol) {
        switch (symbol) {
            case "<":
                return Less;
            case "<=":
                return LessEqual;
            case "=":
                return Equal;
            case "!=":
                return NotEqual;
            case ">":
                return Greater;
            case ">=":
                return GreaterEqual;
            default:
                throw new IllegalArgumentException("Comparison symbol: " + symbol + " not supported.");
        }
    }
}
