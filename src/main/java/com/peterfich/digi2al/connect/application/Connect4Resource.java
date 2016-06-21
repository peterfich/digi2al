package com.peterfich.digi2al.connect.application;

import com.peterfich.digi2al.connect.ConnectService;
import com.peterfich.digi2al.connect.Game;
import com.peterfich.digi2al.connect.Move;
import com.peterfich.digi2al.connect.Parameters;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/connect4")
@Produces(MediaType.APPLICATION_JSON)
public class Connect4Resource {

    private final ConnectService service;

    public Connect4Resource(ConnectService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startGame(Parameters parameters) {
        Game game = service.startGame(parameters.getPlayer1(), parameters.getPlayer2());
        return Response.created(URI.create("/connect4/" + game.getId())).entity(game).build();
    }

    @GET
    @Path("/{id}")
    public Game getGame(@PathParam("id") int id) {
        return service.getGame(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Game makeMove(@PathParam("id") int id, Move move) {
        return service.makeMove(id, move.getPlayer(), move.getColumn());
    }
}