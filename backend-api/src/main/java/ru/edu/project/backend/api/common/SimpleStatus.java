package ru.edu.project.backend.api.common;

public class SimpleStatus implements Status {

    /**
     * Строковый статус.
     */
    private String status;

    /**
     * Конструктор.
     *
     * @param str
     */
    public SimpleStatus(final String str) {
        this.status = str;
    }

    /**
     * Код статуса.
     *
     * @return code
     */
    @Override
    public Long getCode() {
        return null;
    }

    /**
     * Текст статуса.
     *
     * @return code
     */
    @Override
    public String getMessage() {
        return status;
    }
}
