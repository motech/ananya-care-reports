package org.motechproject.carereporting.enums;

public enum FieldType {
    Integer, Numeric, SmallInt, Boolean, String, Date, Timestamp;

    public static FieldType getValueOf(String value) {
        switch (value) {
            case "integer":
                return Integer;
            case "numeric":
                return Numeric;
            case "smallint":
                return SmallInt;
            case "boolean":
                return Boolean;
            case "character varying":
                return String;
            case "date":
                return Date;
            case "timestamp with time zone":
                return Timestamp;
            default:
                return null;
        }
    }
}
