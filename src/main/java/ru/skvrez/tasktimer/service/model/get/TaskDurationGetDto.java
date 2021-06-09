package ru.skvrez.tasktimer.service.model.get;

public class TaskDurationGetDto extends TaskGetDto{

    private Long duration;
    private Integer chunks;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getChunks() {
        return chunks;
    }

    public void setChunks(Integer chunks) {
        this.chunks = chunks;
    }
}
