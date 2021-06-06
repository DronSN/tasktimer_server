package ru.skvrez.tasktimer.service.model.base;

import java.util.List;

/**
 * Represents a page that is returned to client.
 *
 * @param <T> type of content inside the page.
 */
public class PageModel<T> {

    private List<T> content;
    private long totalElements;
    private long page;

    /**
     * Constructor.
     */
    public PageModel(List<T> content) {
        this(content, content.size(), 1);
    }


    /**
     * Constructor.
     */
    public PageModel(List<T> content, long totalElements, long page) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = page;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }
}
