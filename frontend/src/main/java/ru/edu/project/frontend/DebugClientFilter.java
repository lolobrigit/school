package ru.edu.project.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.edu.project.frontend.controller.DebugClientHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class DebugClientFilter implements Filter {

    /**
     * Зависимость.
     */
    @Autowired
    private DebugClientHolder holder;

    /**
     * @inheritDoc
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String setClientId = httpServletRequest.getParameter("setClientId");
        if (setClientId != null) {
            holder.setStubClientId(Long.parseLong(setClientId));
        }

        chain.doFilter(request, response);
    }
}
