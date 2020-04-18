package com.humanresources.assistant.ui.error;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteNotFoundError;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

@Route ("error")
@PageTitle ("Error page")
public class Error extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        getElement().setText(
            "Route you want to access does not exists" +
                Arrays.toString(parameter.getCaughtException().getStackTrace()));
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
