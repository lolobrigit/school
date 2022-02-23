package ru.edu.project.frontend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.requests.RequestService;
import ru.edu.project.backend.api.requests.UpdateStatusRequest;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    /**
     * Имя атрибута с заявками.
     */
    public static final String REQUESTS_ATTR = "requestsPage";

    /**
     * Имя атрибута со списком полей сортировки.
     */
    public static final String ORDER_FIELDS = "orderFields";

    /**
     * Имя атрибута с текущим полем сортировки.
     */
    public static final String ORDER_BY_FIELD = "orderByField";

    /**
     * Имя атрибута с направлением.
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Имя атрибута заявки.
     */
    public static final String RECORD_ATTR = "record";

    /**
     * Имя атрибута статусов заявки.
     */
    public static final String STATUSES_ATTR = "statuses";

    /**
     * Зависимость на сервис заявок.
     */
    @Autowired
    private RequestService requestService;

    /**
     * Просмотр заявок.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @param searchId
     * @return modelAndView
     */
    @GetMapping("/")
    public ModelAndView index(
            @RequestParam(name = "searchBy", required = false, defaultValue = "") final String searchBy,
            @RequestParam(name = "direct", required = false, defaultValue = "1") final boolean isAsc,
            @RequestParam(name = "page", required = false, defaultValue = "1") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "id", required = false, defaultValue = "-1") final int searchId
    ) {

        if (searchId > 0) {
            return new ModelAndView("redirect:/manager/view/" + searchId);
        }

        ModelAndView model = new ModelAndView("manager/index");
        SearchFields searchByField = SearchFields.byString(searchBy);

        /*
         * На странице ведем отсчет страниц с 1, на бэке с 0
         */
        model.addObject(
                REQUESTS_ATTR,
                requestService.searchRequests(RecordSearch.by(searchByField.getField(), isAsc, page - 1, perPage))
        );

        model.addObject(ORDER_FIELDS, SearchFields.values());
        model.addObject(ORDER_BY_FIELD, searchByField);
        model.addObject(IS_ASC, isAsc);

        return model;
    }

    /**
     * Просмотр заявки.
     *
     * @param id
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") final Long id) {

        ModelAndView modelAndView = new ModelAndView("manager/view");
        modelAndView.addObject(RECORD_ATTR, requestService.getDetailedInfo(id));
        modelAndView.addObject(STATUSES_ATTR, RequestStatus.values());

        return modelAndView;
    }


    /**
     * Изменение статуса заявки.
     *
     * @param id
     * @param statusCode
     * @return redirect
     */
    @GetMapping("/view/{id}/setStatus")
    public String updateStatus(@PathVariable("id") final long id, @RequestParam("status") final int statusCode) {

        RequestStatus status = RequestStatus.getByCode(statusCode);

        if (status == null) {
            return "redirect:/manager/view/" + id + "?error=status_invalid";
        }

        boolean res = requestService.updateStatus(UpdateStatusRequest.builder()
                .requestId(id)
                .status(status)
                .build());

        return "redirect:/manager/view/" + id + "?updatedStatus=" + res;
    }

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum RequestStatus implements Status {
        /**
         * Статус.
         */
        CREATED(1L, "Создан"),

        /**
         * Статус.
         */
        RECEIVED(2L, "В работе"),

        /**
         * Статус.
         */
        WAIT_SUPPLY(3L, "Ожидает поставку комплектующих"),

        /**
         * Статус.
         */
        READY(4L, "Готов к выдаче"),

        /**
         * Статус.
         */
        COMPLETED(5L, "Завершено");

        /**
         * Код.
         */
        private Long code;

        /**
         * Текст.
         */
        private String message;

        /**
         * Получаем enum по коду.
         *
         * @param code
         * @return enum or null
         */
        public static RequestStatus getByCode(final int code) {
            return Arrays.stream(values()).filter(e -> e.getCode() == code).findFirst().orElse(null);
        }
    }

    @AllArgsConstructor
    @Getter
    public enum SearchFields {

        /**
         * Поиск по ID.
         */
        ID("id", "id"),

        /**
         * Поиск по дате создания.
         */
        CREATED_AT("createdAt", "дата создания"),

        /**
         * Поиск по последнему обновлению.
         */
        LAST_ACTION_AT("lastActionAt", "последнее обновление");

        /**
         * Поиск по статусу.
         */
        private final String field;

        /**
         * Название поля.
         */
        private final String msg;

        /**
         * Поиск поля по строке из запроса.
         *
         * @param searchBy
         * @return enum
         */
        public static SearchFields byString(final String searchBy) {
            Optional<SearchFields> searchFields = Arrays
                    .stream(values())
                    .filter(e -> e.name().equals(searchBy))
                    .findFirst();

            return searchFields.orElse(CREATED_AT);
        }
    }
}
