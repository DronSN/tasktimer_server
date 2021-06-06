package ru.skvrez.tasktimer.specification;

public enum FilterOperator {
    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    IN("in"),
    LIKE("contains"),
    NOT_LIKE("notContains"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith"),
    GREATER_OR_EQUAL("ge"),
    LESS_OR_EQUAL("le"),
    IS_NULL("isNull");

    private final String operatorName;

    /**
     * Creates filter operator.
     *
     * @param operatorName Operator name.
     */
    FilterOperator(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * Creates {@link FilterOperator} based on operator name.
     *
     * @param operator Operator name.
     * @return {@link FilterOperator}.
     */
    public static FilterOperator fromOperatorName(final String operator) {
        for (final var value : values()) {
            if (value.operatorName.equals(operator)) {
                return value;
            }
        }
        return null;
    }

}
