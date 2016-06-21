package com.peterfich.digi2al.connect.application;

import com.peterfich.digi2al.connect.IllegalMoveException;
import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalMoveExceptionMapper implements ExceptionMapper<IllegalMoveException> {

    @Override
    public Response toResponse(IllegalMoveException e) {
        return Response
                .status(409)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(409, e.getMessage()))
                .build();
    }
}
