package ru.edu.project.backend.api.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
public class PagedView<T> {

    /**
     * Элементы.
     */
    private List<T> elements;

    /**
     * Общее количество элементов.
     */
    private long total;

    /**
     * Всего страниц.
     */
    private int totalPages;

    /**
     * Текущая страница.
     */
    private int page;

    /**
     * Элементов на странице.
     */
    private int perPage;

}
