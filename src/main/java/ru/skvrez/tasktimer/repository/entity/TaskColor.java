package ru.skvrez.tasktimer.repository.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents a task color.
 *
 * @author Andrey Skvortsov
 */
public enum TaskColor {

    GREEN(1),
    YELLOW(2),
    INDIGO(3),
    PINK(4),
    PURPLE(5);

    private final int id;

    TaskColor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Tries to parse given id into {@link TaskColor} instance.
     *
     * @param id id to find matching task color
     * @return task color with matching id or {@literal null}.
     */
    public static TaskColor fromId(final Integer id) {
        if (id == null) {
            return null;
        }
        for (final var value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        return null;
    }

    @JsonCreator
    public static TaskColor fromValue(String source) {
        if (source == null) {
            return null;
        }

        for (final var value : values()) {
            if (value.name().equalsIgnoreCase(source)) {
                return value;
            }
        }
        return null;
    }
}
