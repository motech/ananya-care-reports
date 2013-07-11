package org.motechproject.carereporting.enums;

public enum FieldType {
    Number, Boolean, String, Date, Timestamp;

    public static FieldType getValueOf(String value) {
        switch (value) {
            case "integer":
                return Number;
            case "smallint":
                return Number;
            case "numeric":
                return Number;
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
