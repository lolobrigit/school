package ru.edu.project.frontend.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Для отладки через spring-boot:run не забываем добавить флаг.
 * -Dspring-boot.run.fork=false
 */
@RequestMapping("/client")
@Controller
public class ClientController {

    /**
     * Атрибут модели для хранения списка ошибок.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

    /**
     * Атрибут модели для хранения списка доступных услуг.
     */
    public static final String JOBS_ATTR = "jobs";

    /**
     * Атрибут модели для хранения списка заявок.
     */
    public static final String REQUESTS_ATTR = "requests";
    /**
     * Ссылка на сервис заявок.
     */
    @Autowired
    private RequestService requestService;

    /**
     * Зависимость.
     */
    @Autowired
    private DebugClientHolder clientHolder;

    /**
     * Ссылка на сервис услуг.
     */
    @Autowired
    private JobService jobService;

    /**
     * Отображение заявок клиента.
     *
     * @param model модель для представления
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
        long clientId = clientHolder.getStubClientId(); //в дальнейшим заменим на id пользователя

        model.addAttribute(
                REQUESTS_ATTR,
                requestService.getRequestByClient(clientId)
        );

        return "client/index";
    }

    /**
     * Просмотр заявки по id.
     *
     * @param requestId
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(final @PathVariable("id") Long requestId) {
        long clientId = clientHolder.getStubClientId(); //в дальнейшим заменим на id пользователя

        ModelAndView model = new ModelAndView("client/view");

        RequestInfo detailedInfo = requestService.getDetailedInfo(
                clientId,
                requestId
        );
        if (detailedInfo == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("client/viewNotFound");
            return model;
        }

        model.addObject(
                "record",
                detailedInfo
        );

        return model;
    }

    /**
     * Отображение формы для создания заявки.
     *
     * @param model
     * @return modelAndView
     */
    @GetMapping("/create")
    public String createForm(final Model model) {
        model.addAttribute(JOBS_ATTR, jobService.getAvailable());
        return "client/create";
    }


    /**
     * Получаем форму с предварительной валидацией.
     *
     * @param form
     * @param bindingResult результат валидации формы
     * @param model
     * @return redirect url
     */
    @PostMapping("/create")
    public String createFormProcessing(
            @Valid
            @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            //после добавления ошибок в модель
            return createForm(model); //отображаем форму
        }

        final long clientId = clientHolder.getStubClientId(); //пока константой

        RequestInfo request = requestService.createRequest(RequestForm.builder()
                .clientId(clientId)
                .desiredTimeToVisit(form.getTimeToVisit())
                .selectedJobs(form.getJobs())
                .comment(form.getComment())
                .build());

        return "redirect:/client/?created=" + request.getId();
    }

    /**
     * пример.
     *
     * @return str
     */
    @GetMapping("/method")
    public String exampleForm() {
        return "methodForm";
    }

    /**
     * пример.
     *
     * @param model
     * @return str
     */
    @PutMapping("/method")
    public String examplePut(final Model model) {
        model.addAttribute("method", "put");
        return "method";
    }

    /**
     * пример.
     *
     * @param model
     * @return str
     */
    @DeleteMapping("/method")
    public String exampleDelete(final Model model) {
        model.addAttribute("method", "delete");
        return "method";
    }

    @Getter
    @Setter
    public static class CreateForm {

        /**
         * Для парсинга даты.
         */
        private static final DateFormat FORMAT;

        static {
            FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        }

        /**
         * Выбранные услуги.
         */
        @NotNull
        private List<Long> jobs;

        /**
         * Выбранное время посещения.
         */
        @NotEmpty
        private String visitTime;

        /**
         * Описание проблемы.
         */
        @NotNull
        private String comment;

        /**
         * Получение объекта календаря с временем посещения.
         *
         * @return календарь
         */
        @SneakyThrows
        public Timestamp getTimeToVisit() {
            Date parsed = FORMAT.parse(visitTime);
            return new Timestamp(parsed.getTime());
        }
    }
}
