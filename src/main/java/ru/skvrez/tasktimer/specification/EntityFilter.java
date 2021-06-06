package ru.skvrez.tasktimer.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.skvrez.tasktimer.repository.entity.TaskColor;
import ru.skvrez.tasktimer.repository.entity.TaskStatus;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityFilter<T> {
    Class<T> entity;
    List<FilterConstraint> filterConstraintList;

    public EntityFilter(Class<T> entity, List<FilterConstraint> filterConstraintList) {
        this.entity = entity;
        this.filterConstraintList = filterConstraintList;
    }

    public Class<T> getEntity() {
        return entity;
    }

    public List<FilterConstraint> getFilterConstraintList() {
        return filterConstraintList;
    }

    public Specification<T> getSpecification() {
        if (filterConstraintList == null || filterConstraintList.isEmpty()) {
            return null;
        }
        Set<String> filterOperatorSet = new HashSet<>();
        Specification<T> result = (root, query, criteriaBuilder) -> null;
        for (FilterConstraint filterConstraint : filterConstraintList) {
            if (filterConstraint.filterOperator == FilterOperator.IN) {
                filterOperatorSet.add(filterConstraint.fieldName);
            }
            if (filterConstraint.filterOperator == FilterOperator.IS_NULL &&
                    filterOperatorSet.contains(filterConstraint.fieldName)) {
                result = result.or(createSpecification(filterConstraint));
            } else {
                result = result.and(createSpecification(filterConstraint));
            }
        }
        return result;
    }

    private Specification<T> createSpecification(FilterConstraint filterConstraint) {
        switch(filterConstraint.filterOperator) {
            case IN:
                return BaseFilter.inFilter(filterConstraint.fieldName,
                        convertToFieldValueCollection(filterConstraint.fieldName, filterConstraint.value));
            case LIKE:
                return BaseFilter.likeFilter(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case EQUALS:
                return BaseFilter.equalsFilter(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case NOT_LIKE:
                return BaseFilter.notLike(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case NOT_EQUALS:
                return BaseFilter.notEqualsFilter(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case ENDS_WITH:
                return BaseFilter.endsWith(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case STARTS_WITH:
                return BaseFilter.startsWith(filterConstraint.fieldName,
                        convertToFieldValue(filterConstraint.fieldName, filterConstraint.value));
            case IS_NULL:
                return BaseFilter.isNull(filterConstraint.fieldName);
            default:
                return null;
        }
    }

    private List<Object> convertToFieldValueCollection(String fieldName, List<String> value) {
        if (value.isEmpty()) {
            return Collections.emptyList();
        }
        if (fieldName.equals("color")) {
            return value.stream().map(color -> TaskColor.fromValue(color.trim())).collect(Collectors.toList());
        } else if (fieldName.equals("status")) {
            return value.stream().map(status -> TaskStatus.valueOf(status.trim().toUpperCase())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Object convertToFieldValue(String fieldName, List<String> value) {
        return value.isEmpty() ? null : value.get(0).trim();
    }
}
