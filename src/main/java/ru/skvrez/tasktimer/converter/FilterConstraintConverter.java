package ru.skvrez.tasktimer.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.skvrez.tasktimer.specification.FilterConstraint;
import ru.skvrez.tasktimer.specification.FilterOperator;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterConstraintConverter implements Converter<String[], List<FilterConstraint>> {

    private final static FilterOperator MULTI_VALUE_OPERATOR = FilterOperator.IN;
    private final static String IS_NULL_OPERATOR = "isNull";

    @Override
    public List<FilterConstraint> convert(String[] source) {
        if (source.length < 3 && source.length % 3 != 0) {
            return null;
        }
        List<FilterConstraint> result = new ArrayList<>();
        for (int i = 0; i < source.length; i = i + 3) {
            if (source[i + 1].equals(IS_NULL_OPERATOR) || !(source[i + 2].equals("null") || source[i + 2].equals(""))) {
                FilterOperator filterOperator = FilterOperator.fromOperatorName(source[i + 1]);
                List<String> value;
                if (filterOperator == MULTI_VALUE_OPERATOR) {
                    value = List.of(source[i + 2].split(","));
                } else {
                    value = List.of(source[i + 2]);
                }
                result.add(new FilterConstraint(value, source[i], filterOperator));
            }
        }
        System.out.println(result);
        return result;
    }
}
