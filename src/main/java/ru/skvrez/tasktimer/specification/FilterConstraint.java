package ru.skvrez.tasktimer.specification;

import java.util.List;

public class FilterConstraint {
    List<String> value;
    String fieldName;
    FilterOperator filterOperator;

    public FilterConstraint(List<String> value, String fieldName, FilterOperator filterOperator) {
        this.value = value;
        this.fieldName = fieldName;
        this.filterOperator = filterOperator;
    }

    public List<String> getValue() {
        return value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FilterOperator getFilterOperator() {
        return filterOperator;
    }

    @Override
    public String toString() {
        return "FilterConstraint{" +
                "value=" + value +
                ", fieldName='" + fieldName + '\'' +
                ", filterOperator=" + filterOperator +
                '}';
    }
}
