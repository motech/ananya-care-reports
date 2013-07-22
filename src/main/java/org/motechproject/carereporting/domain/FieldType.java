package org.motechproject.carereporting.domain;

public enum FieldType {
    Number, Boolean, String, Date;

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
                return Date;
            default:
                return null;
        }
    }
}
