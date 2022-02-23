package ru.edu.project.backend.api.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecordSearch {

    /**
     * Значение по умолчанию для perPage.
     */
    public static final int DEFAULT_PER_PAGE = 10;

    /**
     * Значение по умолчанию для page.
     */
    public static final int DEFAULT_PAGE = 1;

    /**
     * Сортировка по полю.
     */
    private String orderBy;

    /**
     * Сортировка по возрастанию.
     */
    private boolean asc;


    /**
     * Страница.
     */
    private int page;


    /**
     * Сколько записей на странице.
     */
    private int perPage;

    /**
     * Фабричный метод.
     *
     * @param field
     * @return obj
     */
    public static RecordSearch by(final String field) {
        return by(field, true, DEFAULT_PAGE, DEFAULT_PER_PAGE);
    }

    /**
     * Фабричный метод.
     *
     * @param field
     * @param direction
     * @return obj
     */
    public static RecordSearch by(final String field, final boolean direction) {
        return by(field, direction, DEFAULT_PAGE, DEFAULT_PER_PAGE);
    }

    /**
     * Фабричный метод.
     *
     * @param field
     * @param direction
     * @param page
     * @return obj
     */
    public static RecordSearch by(final String field, final boolean direction, final int page) {
        return by(field, direction, page, DEFAULT_PER_PAGE);
    }

    /**
     * Фабричный метод.
     *
     * @param field
     * @param direction
     * @param page
     * @param perPage
     * @return obj
     */
    public static RecordSearch by(final String field, final boolean direction, final int page, final int perPage) {

        return RecordSearch.builder()
                .orderBy(field)
                .asc(direction)
                .page(page)
                .perPage(perPage)
                .build();
    }
}
