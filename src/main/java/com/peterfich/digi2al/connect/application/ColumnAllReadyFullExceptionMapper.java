package com.peterfich.digi2al.connect.application;

import com.peterfich.digi2al.connect.ColumnAllReadyFullException;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ColumnAllReadyFullExceptionMapper implements ExceptionMapper<ColumnAllReadyFullException> {

    @Override
    public Response toResponse(ColumnAllReadyFullException e) {
        return Response
                .status(409)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(409, e.getMessage()))
                .build();
    }
}
