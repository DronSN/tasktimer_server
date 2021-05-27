package ru.skvrez.tasktimer.repository.entity;

/**
 * Represents a task status.
 *
 * @author Andrey Skvortsov
 */
public enum TaskStatus {

    STARTED(1),
    PAUSED(2),
    STOPPED(3);

    private final int id;

    TaskStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Tries to parse given id into {@link TaskStatus} instance.
     *
     * @param id id to find matching task status
     * @return task status with matching id or {@literal null}.
     */
    public static TaskStatus fromId(final Integer id) {
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
}
