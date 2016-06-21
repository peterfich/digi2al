package com.peterfich.digi2al.connect.application;

import com.peterfich.digi2al.connect.InvalidColumnException;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidColumnExceptionMapper implements ExceptionMapper<InvalidColumnException> {

    @Override
    public Response toResponse(InvalidColumnException e) {
        return Response
                .status(409)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(409, e.getMessage()))
                .build();
    }
}
