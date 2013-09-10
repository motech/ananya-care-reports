package org.motechproject.carereporting.domain.types;

public enum FieldType {
    Number, Boolean, String, Date;

    public static FieldType getValueOf(String value) {
        switch (value) {
            case "integer":
            case "smallint":
            case "numeric":
                return Number;
            case "boolean":
                return Boolean;
            case "character varying":
            case "text":
                return String;
            case "date":
            case "timestamp with time zone":
            case "timestamp without time zone":
                return Date;
            default:
                return null;
        }
    }
}
