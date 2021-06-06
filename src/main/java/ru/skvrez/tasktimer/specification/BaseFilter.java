package ru.skvrez.tasktimer.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.Collection;
import java.util.Locale;

/**
 * Builds basic specifications.
 *
 */
public class BaseFilter {

    /**
     * Empty protected constructor to allow inheritance.
     */
    protected BaseFilter() {
        // used by hibernate
    }

    /**
     * Returns equals specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> equalsFilter(String attribute, Object value) {
        return ((root, query, builder) -> builder.equal(root.get(attribute), value));
    }

    /**
     * Returns composite specification consisting of equal specifications linked via OR.
     *
     * @param attribute entity attribute by which to filter.
     * @param values    filter values.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> equalsMultiFilter(String attribute, Collection<Object> values) {
        return values
                .stream()
                .map(value -> BaseFilter.<T>equalsFilter(attribute, value))
                .reduce(Specification::or)
                .orElse(null);
    }

    /**
     * Returns equals specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> notEqualsFilter(String attribute, Object value) {
        return ((root, query, builder) -> builder.notEqual(root.get(attribute), value));
    }

    /**
     * Returns equals specification for nested property.
     *
     * @param rootAttribute   entity attribute.
     * @param nestedAttribute nested attribute by which to filter.
     * @param value           filter value.
     * @param <T>             entity type.
     */
    public static <T> Specification<T> nestedEqualsFilter(String rootAttribute, String nestedAttribute, Object value) {
        return (((root, query, builder) -> builder.equal(root.get(rootAttribute).get(nestedAttribute), value)));
    }

    /**
     * Returns <i>IN</i> specification for nested property.
     *
     * @param rootAttribute   entity attribute.
     * @param nestedAttribute nested attribute by which to filter.
     * @param values          desired values for in filter.
     * @param <T>             entity type.
     * @param <V>             value type
     */
    public static <T, V> Specification<T> nestedInFilter(String rootAttribute,
                                                         String nestedAttribute,
                                                         Collection<? extends V> values) {
        return (root, query, builder) -> root.get(rootAttribute).get(nestedAttribute).in(values);
    }

    /**
     * Returns <i>IN</i> specification for top-level property.
     *
     * @param rootAttribute   entity attribute.
     * @param values          desired values for in filter.
     * @param <T>             entity type.
     * @param <V>             value type
     */
    public static <T, V> Specification<T> inFilter(String rootAttribute,
                                                   Collection<? extends V> values) {
        return (root, query, builder) -> root.get(rootAttribute).in(values);
    }


    /**
     * Returns like specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> likeFilter(String attribute, Object value) {
        return ((root, query, builder) -> {
            final String pattern = ("%" + value + "%").toLowerCase(Locale.US);
            return builder.like(builder.lower(root.get(attribute)), pattern);
        });
    }

    /**
     * Returns like specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> startsWith(String attribute, Object value) {
        return ((root, query, builder) -> {
            final String pattern = (value + "%").toLowerCase(Locale.US);
            return builder.like(builder.lower(root.get(attribute)), pattern);
        });
    }

    /**
     * Returns like specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> endsWith(String attribute, Object value) {
        return ((root, query, builder) -> {
            final String pattern = ("%" + value).toLowerCase(Locale.US);
            return builder.like(builder.lower(root.get(attribute)), pattern);
        });
    }

    /**
     * Returns like specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param value     filter value.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> notLike(String attribute, Object value) {
        return ((root, query, builder) -> {
            final String pattern = ("%" + value + "%").toLowerCase(Locale.US);
            return builder.notLike(builder.lower(root.get(attribute)), pattern);
        });
    }

    /**
     * Returns like specification for nested property.
     *
     * @param rootAttribute   entity attribute.
     * @param nestedAttribute nested attribute by which to filter.
     * @param value           filter value.
     * @param <T>             entity type.
     */
    public static <T> Specification<T> nestedLikeFilter(String rootAttribute, String nestedAttribute, Object value) {
        return ((root, query, builder) -> {
            final String pattern = ("%" + value + "%").toLowerCase(Locale.US);
            return builder.like(builder.lower(root.get(rootAttribute).get(nestedAttribute)), pattern);
        });
    }

    /**
     * Returns like specification.
     *
     * @param attribute entity attribute by which to filter.
     * @param <T>       entity type.
     */
    public static <T> Specification<T> isNull(String attribute) {
        return ((root, query, builder) -> builder.isNull(root.get(attribute)));
    }
}
