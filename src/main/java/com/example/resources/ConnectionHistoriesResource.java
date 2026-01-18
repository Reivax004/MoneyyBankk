package com.example.resources;

import com.example.models.ConnectionHistory;
import com.example.service.ConnectionHistoriesService;
import com.example.responses.SuccessResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/connection-histories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConnectionHistoriesResource {

    @Inject
    private ConnectionHistoriesService connectionHistoriesService;

    @GET
    @Path("/all")
    public Response list() {
        List<ConnectionHistory> connectionHistoryList =
                connectionHistoriesService.findAllConnectionHistories();
        if (connectionHistoryList.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return SuccessResponse.of("Connection histories fetched successfully", connectionHistoryList);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        List<ConnectionHistory> connectionHistoryList = connectionHistoriesService.findAllConnectionHistoriesOfUser(id);
        if (connectionHistoryList.isEmpty()) {
            throw new NotFoundException("User %d not found".formatted(id));
        }
        return SuccessResponse.of("Connection histories of user fetched successfully", connectionHistoryList);
    }
}
