package org.motechproject.carereporting.domain.types;

public enum FrequencyType {
    EVERY_SECOND("Every second"),
    EVERY_MINUTE("Every minute"),
    EVERY_HOUR("Every hour"),
    EVERY_DAY("Every day"),
    EVERY_MONTH("Every month"),
    EVERY_YEAR("Every year");

    private String name;

    private FrequencyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FrequencyType fromValue(String value) {
        for (FrequencyType frequencyType: FrequencyType.values()) {
            if (frequencyType.getName().equals(value)) {
                return frequencyType;
            }
        }

        return null;
    }
}
