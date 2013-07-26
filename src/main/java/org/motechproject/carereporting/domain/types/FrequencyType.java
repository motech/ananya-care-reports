package org.motechproject.carereporting.domain.types;

public enum FrequencyType {
    EVERY_SECOND("Every second", "* * * * * ?"),
    EVERY_MINUTE("Every minute", "0 * * * * ?"),
    EVERY_HOUR("Every hour", "0 0 * * * ?"),
    EVERY_DAY("Every day", "0 0 0 * * ?"),
    EVERY_MONTH("Every month", "0 0 0 0 * ?"),
    EVERY_YEAR("Every year", "0 0 0 0 0 ?");

    private String name;
    private String expression;

    private FrequencyType(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }
}
