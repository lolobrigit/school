package ru.edu.project.frontend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.authorization.UserDetailsId;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClientControllerTest {

    public static final Long CLIENT_ID = 123L;

    @Mock
    private UserDetailsId detailsId;

    @Mock
    private RequestService requestService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model modelMock;

    @Mock
    private JobService jobService;

    @InjectMocks
    private ClientController clientController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(CLIENT_ID);
    }

    @Test
    public void view() {
        long requestId = 333L;

        RequestInfo expectedRequestInfo = RequestInfo.builder().build();
        when(requestService.getDetailedInfo(CLIENT_ID, requestId)).thenReturn(expectedRequestInfo);

        ModelAndView view = clientController.view(requestId, authentication);

        assertEquals("client/view", view.getViewName());

        assertEquals(expectedRequestInfo, view.getModel().get("record"));
    }

    @Test
    public void createForm() {
        ArrayList<Job> expectedJobList = new ArrayList<>();
        when(jobService.getAvailable()).thenReturn(expectedJobList);

        String viewName = clientController.createForm(modelMock);

        assertEquals("client/create", viewName);
        verify(modelMock).addAttribute("jobs", expectedJobList);
    }

    @Test
    public void createFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        RequestInfo expectedInfo = RequestInfo.builder().id(444L).build();

        ClientController.CreateForm createForm = new ClientController.CreateForm();
        createForm.setVisitTime("2020-12-01T23:00");
        createForm.setComment("commentStr");


        when(requestService.createRequest(any(RequestForm.class))).thenAnswer(invocation -> {
            RequestForm form = invocation.getArgument(0, RequestForm.class);
            assertEquals(CLIENT_ID, form.getClientId());
            assertEquals(createForm.getTimeToVisit(), form.getDesiredTimeToVisit());
            assertEquals(createForm.getComment(), form.getComment());
            return expectedInfo;
        });


        String viewName = clientController.createFormProcessing(createForm, bindingResultMock, modelMock, authentication);

        assertEquals("redirect:/client/?created="+expectedInfo.getId(), viewName);

        verify(modelMock, times(0)).addAttribute(anyString(), any());
        verify(requestService).createRequest(any(RequestForm.class));
    }

    @Test
    public void createFormProcessingHasErrors(){
        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        ArrayList<Job> expectedJobList = new ArrayList<>();
        when(jobService.getAvailable()).thenReturn(expectedJobList);

        String viewName = clientController.createFormProcessing(
                new ClientController.CreateForm(),
                bindingResultMock,
                modelMock,
                authentication
        );

        assertEquals("client/create", viewName);
        verify(modelMock).addAttribute("jobs", expectedJobList);
        verify(modelMock).addAttribute("errorsList", mockErrors);


    }
}