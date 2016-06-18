package com.peterfich.digi2al.connect4;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/connect4")
@Produces(MediaType.APPLICATION_JSON)
public class Connect4Resource {

    public Connect4Resource() {
    }

    @GET
    public State sayHello() {
        State state = new State();
        state.setState("hello");
        return state;
    }
}