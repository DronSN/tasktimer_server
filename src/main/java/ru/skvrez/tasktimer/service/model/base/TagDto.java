package ru.skvrez.tasktimer.service.model.base;

public class TagDto {
    private String name;

    public TagDto() {}

    public TagDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
